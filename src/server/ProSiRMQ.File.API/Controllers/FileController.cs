using Microsoft.AspNetCore.Mvc;
using ProSiRMQ.Infrastructure.Interfaces;
using ProSiRMQ.Infrastructure.Services;

namespace ProSiRMQ.File.API.Controllers;

[ApiController]
[Route("file")]
public class FileController : ControllerBase
{
    private readonly ILogger<FileController> _logger;
    private readonly IFileProvider _fileProvider;

    public FileController(ILogger<FileController> logger, IFileProvider fileProvider)
    {
        
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
    public async Task<IActionResult> SaveAsync(IFormFile file, CancellationToken token = new())
    {
        var result = await _fileProvider.SaveFileAsync(file, token);

        return Ok(result);
    }
}