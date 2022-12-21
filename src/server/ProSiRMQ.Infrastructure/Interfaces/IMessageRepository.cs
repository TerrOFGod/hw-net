using ProSiRMQ.Infrastructure.Models;
using ProSiRMQ.Infrastructure.Services;

namespace ProSiRMQ.Infrastructure.Interfaces;

public interface IMessageRepository : IRepository<Message>
{
    Task<Message> CreateMessageAsync(string? sender, string content, Guid? key);
    Task AddMessageAsync(Message message, CancellationToken token = default);
    Task<List<Message>> GetHistory(int size, CancellationToken token = default);
}