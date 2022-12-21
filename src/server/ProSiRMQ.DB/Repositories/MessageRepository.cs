using System.Data.Common;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using ProSiRMQ.DB.Mappers;
using ProSiRMQ.Infrastructure.Interfaces;
using ProSiRMQ.Infrastructure.Models;
using ProSiRMQ.Infrastructure.Services;

namespace ProSiRMQ.DB.Repositories;

public class MessageRepository : IMessageRepository
{
    private readonly AppDbContext _context;
    private readonly ILogger<MessageRepository> _logger;

    public MessageRepository(AppDbContext context, ILogger<MessageRepository> logger)
    {
        _context = context;
        _logger = logger;
    }
    
    public async Task<List<Message>> GetHistory(int size, CancellationToken token = default)
    {
        try
        {
            var listByDesc = await _context.Messages
                .OrderByDescending(msg => msg.Published)
                .Take(size)
                .OrderBy(msg => msg.Published)
                .ToListAsync(token);
            return listByDesc
                .Select(message => new Message(message.Sender, message.Content, message.FileKey))
                .ToList();
        }
        catch (DbException ex)
        {
            _logger.LogError("The exception was thrown: {ex}", ex);
            return null;
        }
    }
    
    public Task<Message> CreateMessageAsync(string? sender, string content, Guid? key)
    {
        var message = new Message(DateTime.UtcNow, sender, content, key);
        return Task.FromResult(message);
    }

    public async Task AddMessageAsync(Message message, CancellationToken token = default)
    {
        var dbMessage = message.ToDbModel();
        await _context.AddAsync(dbMessage, token);
        await _context.SaveChangesAsync(token);
    }

    public Task<List<Message>> GetHistory(int page, int size, bool fromEnd, CancellationToken token = default)
    {
        throw new NotImplementedException();
    }

    public Guid AddAsync(Message item)
    {
        var dbMessage = item.ToDbModel();
        _context.Messages.AddAsync(dbMessage);
        _context.SaveChangesAsync();
        return dbMessage!.Id;
    }

    public Task<Message?> FindByIdAsync(int id)
    {
        throw new NotImplementedException();
    }

    public async Task<Models.Message?> FindByIdAsync(Guid id)
    {
        // Better SingleOrDefaultAsync
        return await _context.Messages.Where(mes => mes.Id == id)
            .SingleOrDefaultAsync();
    }

    public async Task UpdateAsync(Message item)
    {
        await _context.SaveChangesAsync();
    }

    public async Task RemoveAsync(Message item)
    {
        _context.Messages.Remove(await FindByIdAsync(item.Id));
        await _context.SaveChangesAsync();
    }
}