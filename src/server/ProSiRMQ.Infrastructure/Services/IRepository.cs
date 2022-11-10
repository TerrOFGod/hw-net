namespace ProSiRMQ.Infrastructure.Services;

public interface IRepository<TItem> where TItem: class
{
    Guid AddAsync(TItem item);
    Task<TItem?> FindByIdAsync(int id);
    Task UpdateAsync(TItem item);
    Task RemoveAsync(TItem item);
}