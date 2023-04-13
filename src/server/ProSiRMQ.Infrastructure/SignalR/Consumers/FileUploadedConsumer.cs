using MassTransit;
using Microsoft.AspNetCore.SignalR;
using ProSiRMQ.Infrastructure.Events;
using ProSiRMQ.Infrastructure.Interfaces;
using ProSiRMQ.Infrastructure.SignalR.Hubs;

namespace ProSiRMQ.Infrastructure.SignalR.Consumers;

public class FileUploadedConsumer : IConsumer<FileUploaded>
{
    private readonly IHubContext<ChatHub> _hubContext;
    private readonly ICacheService _cachingService;

    public FileUploadedConsumer(IHubContext<ChatHub> hubContext, ICacheService cachingService)
    {
        _hubContext = hubContext;
        _cachingService = cachingService;
    }

    
    public async Task Consume(ConsumeContext<FileUploaded> context)
    {
        var requestId = context.Message.RequestId;
        var fileKeyTask = _cachingService.FindFileIdAsync(requestId);
        
        var fileKey = await fileKeyTask;

        await _hubContext.Clients.All
            .SendAsync("ReceiveFileUploaded", fileKey);
    }
}