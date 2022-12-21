using MassTransit;
using Microsoft.AspNetCore.SignalR;
using Microsoft.Extensions.Logging;
using ProSiRMQ.Infrastructure.Dto;
using ProSiRMQ.Infrastructure.Models;

namespace ProSiRMQ.Infrastructure.SignalR.Hubs;

public class ChatHub : Hub
{
    private const string PublishMessageMethodName = "PublishMessage";
    
    private readonly IBus _bus;
    private readonly ILogger<ChatHub> _logger;

    public ChatHub(IBus bus, ILogger<ChatHub> logger)
    {
        _bus = bus;
        _logger = logger;
    }
    
    public async Task SendMessage(ReadMessageDto message)
    {
        var publish = _bus.Publish(new MessagePublished {Sender = message.Sender, Content = message.Content, 
            FileKey = string.IsNullOrEmpty(message.FileKey) ? null : Guid.Parse((ReadOnlySpan<char>) message.FileKey)});
        var send = Clients.All.SendAsync(PublishMessageMethodName, message);
        await Task.WhenAll(publish, send);
    }

}