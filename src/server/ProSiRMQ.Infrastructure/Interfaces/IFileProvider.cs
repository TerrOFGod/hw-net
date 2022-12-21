using Microsoft.AspNetCore.Http;
using ProSiRMQ.Infrastructure.Models;

namespace ProSiRMQ.Infrastructure.Interfaces;

public interface IFileProvider
{
    Task<CustomFile> FindFileAsync(string key, CancellationToken token = default);
    Task<CustomFileInfo> SaveFileAsync(IFormFile file, CancellationToken token = default);
}