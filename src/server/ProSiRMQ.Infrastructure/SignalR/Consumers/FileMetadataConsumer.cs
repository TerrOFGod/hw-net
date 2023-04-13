using MassTransit;
using ProSiRMQ.Infrastructure.Events;
using ProSiRMQ.Infrastructure.Interfaces;

namespace ProSiRMQ.Infrastructure.SignalR.Consumers;

public class FileMetadataConsumer : IConsumer<MetadataUploaded>
{
    private readonly ICacheService _cacheService;
    private readonly IMetadataRepository _metadataRepository;
    private readonly IFileMover _fileMover;
    private readonly IBus _bus;

    public FileMetadataConsumer(ICacheService cacheService, IMetadataRepository metadataRepository,
        IFileMover fileMover, IBus bus)
    {
        _cacheService = cacheService;
        _metadataRepository = metadataRepository;
        _fileMover = fileMover;
        _bus = bus;
    }
    
    
    public async Task Consume(ConsumeContext<MetadataUploaded> context)
    {
        var requestId = context.Message.RequestId;
        var increment = await _cacheService.IncrementAsync(requestId);

        if (increment != 2)
            return;

        var fileId = await _cacheService.FindFileIdAsync(requestId);

        if (!fileId.HasValue)
            throw new Exception("File don't exist in cache.");

        await SaveMetadataAsync(requestId, fileId.Value);
        await MoveFileToPersistenceBucketAsync(fileId.Value);

        var fileUploadedEvent = context.Message;
        await _bus.Publish(fileUploadedEvent);
    }
    
    private async Task MoveFileToPersistenceBucketAsync(Guid fileId)
    {
        var isMoved = await _fileMover.MoveToPersistenceAsync(fileId);

        if (!isMoved)
            throw new Exception("Trouble while moving to persistent bucket");
    }

    private async Task SaveMetadataAsync(Guid requestId, Guid fileId)
    {
        var metadata = await _cacheService.FindMetadataAsync(requestId);

        if (metadata is null)
            return;

        var isSaved = await _metadataRepository.SaveMetadataAsync(fileId, metadata);

        if (!isSaved)
            throw new Exception("Trouble while saving metadata of file");
    }
}