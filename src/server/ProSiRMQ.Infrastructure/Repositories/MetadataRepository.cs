using Microsoft.Extensions.Logging;
using MongoDB.Bson;
using MongoDB.Driver;
using ProSiRMQ.Infrastructure.Interfaces;

namespace ProSiRMQ.Infrastructure.Repositories;

public class MetadataRepository : IMetadataRepository
{
    private readonly IMongoCollection<BsonDocument> _collection;
    private readonly ILogger<MetadataRepository> _logger;

    public MetadataRepository(IMongoDatabase database, ILogger<MetadataRepository> logger)
    {
        _logger = logger;
        _collection = database.GetCollection<BsonDocument>("metadata");
    }
    
    public async Task<bool> SaveMetadataAsync(Guid fileId, Dictionary<string, string> metadata)
    {
        var document = new BsonDocument(metadata)
        {
            {"fileId", fileId}
        };

        try
        {
            await _collection.InsertOneAsync(document);
            return true;
        }
        catch (MongoException ex)
        {
            // ReSharper disable once TemplateIsNotCompileTimeConstantProblem
            _logger.LogError(ex.Message);
            return false;
        }
    }
}