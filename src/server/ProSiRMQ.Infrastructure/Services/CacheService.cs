using System.Text.Json;
using ProSiRMQ.Infrastructure.Dto;
using ProSiRMQ.Infrastructure.Interfaces;
using StackExchange.Redis;

namespace ProSiRMQ.Infrastructure.Services;

public class CacheService : ICacheService
{
    private const string FileIdField = "File Id";
    private const string MetadataField = "Metadata";
    private const string IncrementField = "Increment";
    
    private readonly IDatabase _caching;

    public CacheService(IDatabase caching)
    {
        _caching = caching;
    }
    
    public async Task SaveMetadataAsync(MetadataDto dto)
    {
        var json = JsonSerializer.Serialize(dto.Metadata);
        await _caching.HashSetAsync(dto.RequestId.ToString(), MetadataField, json);
    }

    public async Task<long> IncrementAsync(Guid requestId)
    {
        return await _caching.HashIncrementAsync(requestId.ToString(), IncrementField);
    }

    public async Task SaveFileIdAsync(Guid requestId, Guid fileId)
    {
        await _caching.HashSetAsync(requestId.ToString(), FileIdField, fileId.ToString());
    }

    public async Task<Guid?> FindFileIdAsync(Guid requestId)
    {
        var fileId = await _caching.HashGetAsync(requestId.ToString(), FileIdField);
        
        return fileId.HasValue
            ? Guid.Parse(fileId.ToString())
            : null;
    }

    public async Task<Dictionary<string, string>?> FindMetadataAsync(Guid requestId)
    {
        var json = await _caching.HashGetAsync(requestId.ToString(), MetadataField);

        return json.HasValue
            ? JsonSerializer.Deserialize<Dictionary<string, string>>(json.ToString())
            : null;
    }
}