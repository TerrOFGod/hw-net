using ProSiRMQ.Infrastructure.Dto;

namespace ProSiRMQ.Infrastructure.Interfaces;

public interface ICacheService
{
    Task SaveMetadataAsync(MetadataDto dto);
    Task<long> IncrementAsync(Guid requestId);
    Task SaveFileIdAsync(Guid requestId, Guid fileId);

    Task<Guid?> FindFileIdAsync(Guid requestId);
    Task<Dictionary<string, string>?> FindMetadataAsync(Guid requestId);
}