namespace ProSiRMQ.Infrastructure.Interfaces;

public interface IMetadataRepository
{
    Task<bool> SaveMetadataAsync(Guid fileId, Dictionary<string, string> metadata);
}