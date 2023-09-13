using CollectIt.Database.Entities.Resources;
using CollectIt.Database.Infrastructure.Settings;
using Confluent.Kafka;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using MongoDB.Driver;
using MongoDB.Driver.Linq;

namespace CollectIt.Mobile.Server.ResourcesStatistics.Controllers
{
    [ApiController]
    [Route("traffic")]
    public class ResourcesTrafficController : ControllerBase
    {
        private readonly IMongoClient _mongoClient;
        private readonly IProducer<Null, Guid> _producer;
        private readonly MongoSettings _mongoSettings;
        private readonly KafkaSettings _kafkaSettings;

        public ResourcesTrafficController(IMongoClient mongoClient, IOptions<MongoSettings> mongoSettings,
             IProducer<Null, Guid> producer, IOptions<KafkaSettings> kafkaSettings)
        {
            _mongoClient = mongoClient;
            _mongoSettings = mongoSettings.Value;
            _producer = producer;
            _kafkaSettings = kafkaSettings.Value;
        }

        [HttpGet]
        public async Task<Dictionary<string, int>> GetResourceTrafficAsync()
        {
            var mongoDatabase = _mongoClient.GetDatabase(_mongoSettings.DatabaseName);
            var collection = mongoDatabase.GetCollection<ResourseTraffic>(_mongoSettings.ResourceTrafficCollectionName);

            var topTraffic = await collection
                .AsQueryable()
                .GroupBy(t => t.ResourceId)
                .Select(g => new { Id = g.Key, Traffic = g.Count() })
                .OrderByDescending(t => t.Traffic)
                .Take(10)
                .ToListAsync();

            var dictionary = topTraffic.ToDictionary(
                t => t.Id.ToString(),
                t => t.Traffic);

            return dictionary;
        }

        [HttpPost]
        [Route("/resource/{resourceId:guid}")]
        public async Task<IActionResult> RegisterResourceTrafficAsync(Guid resourceId)
        {
            await _producer.ProduceAsync(_kafkaSettings.Topic, new Message<Null, Guid>()
            {
                Value = resourceId
            });
            return Ok();
        }
    }
}
