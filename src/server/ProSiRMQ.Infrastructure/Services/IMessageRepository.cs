using ProSiRMQ.Infrastructure.Models;

namespace ProSiRMQ.Infrastructure.Services;

public interface IMessageRepository : IRepository<Message>
{
    Task<Message> CreateMessageAsync(string? sender, string content);
    Task AddMessageAsync(Message message, CancellationToken token = default);
    Task<List<Message>> GetHistory(int size, CancellationToken token = default);
}