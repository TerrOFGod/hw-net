using Microsoft.AspNetCore.Mvc;
using ProSiRMQ.Infrastructure.Dto;
using ProSiRMQ.Infrastructure.Services;

namespace ProSiRMQ.API.Controllers;

[ApiController]
[Route("api")]
public class MessageController : Controller
{
    private readonly IMessageRepository _messageRepository;
    private readonly ILogger<MessageController> _logger;

    public MessageController(IMessageRepository messageRepository, ILogger<MessageController> logger)
    {
        _messageRepository = messageRepository;
        _logger = logger;
    }
    
    [HttpGet("/messages/{count}")]
    public async Task<ActionResult<ReadMessageDto>> GetMessages(int count)
    {
        _logger.LogInformation("Size: {Size}", count);
        return Ok( ( await _messageRepository.GetHistory(count) )
                  .Select(ReadMessageDto.FromMessage) );
    }
}