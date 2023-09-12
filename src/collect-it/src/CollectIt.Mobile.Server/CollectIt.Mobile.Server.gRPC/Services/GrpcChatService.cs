using System.Collections.Concurrent;
using ChatPackage;
using CollectIt.Mobile.Server.gRPC.Dtos;
using Google.Protobuf.WellKnownTypes;
using Grpc.Core;

namespace CollectIt.Mobile.Server.gRPC.Services;

public class GrpcChatService : ChatService.ChatServiceBase
{
    private static readonly ConcurrentQueue<string> FreeAdmins = new();
    private static readonly ConcurrentDictionary<UserDto, UserDto> Rooms = new();
    private static readonly Empty Empty = new();
    private readonly ILogger<GrpcChatService> _logger;

    public GrpcChatService(ILogger<GrpcChatService> logger)
    {
        _logger = logger;
    }

    public override Task<Empty> Join(JoinRequest request, ServerCallContext context)
    {
        var username = request.Username;
        FreeAdmins.Enqueue(username);
        return Task.FromResult(Empty);
    }

    public override Task<SendMessageResponse> SendMessage(SendMessageRequest request, ServerCallContext context)
    {
        try
        {
            var username = request.Username;
            var isAdmin = username.Contains("Admin");

            ValidateSendMessage(username, isAdmin);
            _logger.LogInformation("Получено сообщение от клиента");
            if (!isAdmin && Rooms.All(r => r.Value.Username != username))
            {
                FreeAdmins.TryDequeue(out var adminName);
                var user = new UserDto(username);
                var admin = new UserDto(adminName!);
                var message = new MessageDto(username, request.Message);
                admin.Messages.Enqueue(message);
                admin.Events.Enqueue("UserJoined");
                user.Messages.Enqueue(message);
                Rooms[admin] = user;
            }
            else
            {
                var room = Rooms
                    .First(r => r.Key.Username == username || r.Value.Username == username);

                var message = new MessageDto(username, request.Message);
                room.Key.Messages.Enqueue(message);
                room.Value.Messages.Enqueue(message);
            }
            _logger.LogInformation("Сообщение от пользователя обработано");

            return Task.FromResult(new SendMessageResponse());

        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "Ошибка во время отправки сообщения");
            throw;
        }
    }

    public override async Task GetMessagesStream(GetMessagesStreamRequest request, IServerStreamWriter<GetMessagesStreamResponse> responseStream, ServerCallContext context)
    {
        try
        {
            var username = request.Username;

            if (Rooms.All(r => r.Key.Username != username && r.Value.Username != username))
            {
                throw new RpcException(Status.DefaultCancelled);
            }

            var room = Rooms
                .First(r => r.Key.Username == username || r.Value.Username == username);

            var isAdmin = username.Contains("Admin");

            var user = isAdmin
                ? room.Key
                : room.Value;

            try
            {
                while (!context.CancellationToken.IsCancellationRequested)
                {
                    await Task.Delay(1000);

                    while (user.Messages.TryDequeue(out var message))
                    {
                        var response = new GetMessagesStreamResponse
                        {
                            Message = message.Text,
                            Username = message.Username
                        };

                        await responseStream.WriteAsync(response);
                    }
                }
            }
            catch (OperationCanceledException)
            {

            }
            finally
            {
                var admin = Rooms
                    .First(r => r.Key.Username == username || r.Value.Username == username)
                    .Key;

                Rooms.Remove(admin, out _);
            }
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "Ошибка подписки на канал сообщений");
        }
    }

    public override async Task GetInfo(GetInfoRequest request, IServerStreamWriter<GetInfoResponse> responseStream, ServerCallContext context)
    {
        var username = request.Username;

        while (true)
        {
            var admin = Rooms.Select(e => e.Key).FirstOrDefault(e => e.Username == username);

            if (admin is null)
            {
                continue;
            }

            while (admin.Events.TryDequeue(out var e))
            {
                await responseStream.WriteAsync(new GetInfoResponse
                {
                    EventText = e
                });
            }

            await Task.Delay(1000);
        }
    }

    private static void ValidateSendMessage(string username, bool isAdmin)
    {
        switch (isAdmin)
        {
            case true when Rooms.All(r => r.Key.Username != username):
                throw new RpcException(Status.DefaultCancelled);

            case false when !FreeAdmins.Any() && Rooms.All(r => r.Value.Username != username):
                throw new RpcException(Status.DefaultCancelled);

        }
    }
}