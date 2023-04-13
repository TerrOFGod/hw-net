using MassTransit;
using Microsoft.AspNetCore.Mvc;
using ProSiRMQ.Infrastructure.Dto;
using ProSiRMQ.Infrastructure.Interfaces;
using ProSiRMQ.Infrastructure.Mappers;
using ProSiRMQ.Infrastructure.Models;

namespace ProSiRMQ.Metadata.API.Controllers;

[ApiController]
[Route("metadata")]
public class MetadataController : ControllerBase
{
    private readonly ICacheService _cacheService; 
    private readonly IBus _bus;
    private readonly ILogger<MetadataController> _logger;

    public MetadataController(ICacheService cacheService, IBus bus, ILogger<MetadataController> logger)
    {
        _cacheService = cacheService;
        _bus = bus;
        _logger = logger;
    }
    
    [HttpPost]
    public async Task<IActionResult> UploadMetadata(MetadataDto dto, 
        CancellationToken token = default)
    {
        _logger.LogInformation("Metadata saving requested for request {RequestId}", dto.RequestId);
        try
        {
            await _cacheService.SaveMetadataAsync(dto);
        }
        catch (Exception ex)
        {
            _logger.LogWarning(ex, "Error during saving metadata to cache");
            return Problem("Could not save metadata to cache");
        }
        _logger.LogInformation("Metadata was saved to cache. Publishing event");

        await _bus.Publish(dto.ToMetadataUploaded(), token);
        _logger.LogInformation("Event was published");
        return Ok();
    }
}