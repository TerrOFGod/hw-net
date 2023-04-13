using Microsoft.AspNetCore.Http;

namespace ProSiRMQ.Infrastructure.Dto;

public class FileDto
{
    public Guid RequestId { get; set; }
    public IFormFile File { get; set; }
}