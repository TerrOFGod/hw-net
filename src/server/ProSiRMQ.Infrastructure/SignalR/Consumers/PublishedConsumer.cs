using MassTransit;
using Microsoft.Extensions.Logging;
using ProSiRMQ.Infrastructure.Models;
using ProSiRMQ.Infrastructure.Services;

namespace ProSiRMQ.Infrastructure.SignalR.Consumers;

public class PublishedConsumer: IConsumer<MessagePublished>
{
    private readonly IMessageRepository _repository;
    private readonly ILogger<PublishedConsumer> _logger;

    public PublishedConsumer(ILogger<PublishedConsumer> logger, IMessageRepository repository)
    {
        _repository = repository;
        _logger = logger;
    }
    
    public async Task Consume(ConsumeContext<MessagePublished> context)
    {
        try
        {
            _logger.LogInformation("Requested saving message: {Content} from: {From}", 
                context.Message.Content, context.Message.Sender);
            var message = await _repository.CreateMessageAsync(context.Message.Sender, context.Message.Content);
            await _repository.AddMessageAsync(message, context.CancellationToken);
        }
        catch (Exception e)
        {
            _logger.LogWarning(e,
                               "Error while saving message to database. "
                             + "Sender: \"{Sender}\". "
                             + "Content: \"{Content}\"", 
                               context.Message.Sender,
                               context.Message.Content);
        }
    }
}