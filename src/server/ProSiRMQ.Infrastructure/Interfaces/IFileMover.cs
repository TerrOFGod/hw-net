namespace ProSiRMQ.Infrastructure.Interfaces;

public interface IFileMover
{
    Task<bool> MoveToPersistenceAsync(Guid fileId);
}