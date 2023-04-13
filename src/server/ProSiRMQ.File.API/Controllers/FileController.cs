using MassTransit;
using Microsoft.AspNetCore.Mvc;
using ProSiRMQ.Infrastructure.Dto;
using ProSiRMQ.Infrastructure.Events;
using ProSiRMQ.Infrastructure.Interfaces;
using ProSiRMQ.Infrastructure.Mappers;
using ProSiRMQ.Infrastructure.Models;
using ProSiRMQ.Infrastructure.Services;

namespace ProSiRMQ.File.API.Controllers;

[ApiController]
[Route("file")]
public class FileController : ControllerBase
{
    private readonly ILogger<FileController> _logger;
    private readonly IFileProvider _fileProvider;
    private readonly IBus _bus;
    private readonly ICacheService _cacheService;

    public FileController(ILogger<FileController> logger, IFileProvider fileProvider, IBus bus, 
        ICacheService cacheService)
    {
        _cacheService = cacheService;
        _bus = bus;
        _logger = logger;
        _fileProvider = fileProvider;
    }

    [HttpGet("{key}")]
    public async Task<IActionResult> GetAsync(string key, CancellationToken token = new())
    {
        var result = await _fileProvider.FindFileAsync(key, token);

        if (result == null)
        {
            return UnprocessableEntity($"CustomFile not found: {key}");
        }

        var file = File(result.Body, result.ContentType, result.Name);
        
        _logger.LogWarning("Send File: {file}, {type}", file, file.ContentType);

        return File(result.Body, result.ContentType, result.Name);
    }

    [HttpPost]
    public async Task<IActionResult> SaveAsync(FileDto fileDto, CancellationToken token = new())
    {
        var result = await _fileProvider.SaveFileAsync(fileDto.File, token);
        var fileUploaded = fileDto.ToFileUploaded(Guid.Parse(result.Key));

        await _cacheService.SaveFileIdAsync(fileDto.RequestId, Guid.Parse(result.Key));
        await _bus.Publish(fileUploaded, token);

        return Ok(result);
    }
}