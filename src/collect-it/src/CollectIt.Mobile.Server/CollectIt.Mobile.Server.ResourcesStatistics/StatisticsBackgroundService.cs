using CollectIt.Database.Infrastructure.Settings;
using Microsoft.Extensions.Options;
using MongoDB.Driver;
using System.Text.Json;
using System.Text;
using RabbitMQ.Client;
using CollectIt.Database.Entities.Resources;
using MongoDB.Driver.Linq;

namespace CollectIt.Mobile.Server.ResourcesStatistics
{
    public sealed class StatisticsBackgroundService : BackgroundService
    {
        private readonly IMongoClient _mongoClient;
        private readonly MongoSettings _mongoSettings;

        public StatisticsBackgroundService(IMongoClient mongoClient, IOptions<MongoSettings> mongoSettings)
        {
            _mongoClient = mongoClient;
            _mongoSettings = mongoSettings.Value;
        }

        protected override async Task ExecuteAsync(CancellationToken stoppingToken)
        {
            var factory = new ConnectionFactory
            {
                Uri = new Uri("amqp://guest:guest@localhost:5672")
            };

            using var channel = factory.CreateConnection().CreateModel();

            while (!stoppingToken.IsCancellationRequested)
            {
                var mongoDatabase = _mongoClient.GetDatabase(_mongoSettings.DatabaseName);
                var collection = mongoDatabase.GetCollection<ResourseTraffic>(_mongoSettings.ResourceTrafficCollectionName);

                var traffics = await collection
                    .AsQueryable()
                    .GroupBy(t => t.ResourceId)
                    .Select(g => new { Id = g.Key, Traffic = g.Count() })
                    .ToListAsync(stoppingToken);

                var dict = traffics.ToDictionary(e => e.Id, e => e.Traffic);
                var body = Encoding.UTF8.GetBytes(JsonSerializer.Serialize(dict));

                var properties = channel.CreateBasicProperties();
                properties.Persistent = true;
                properties.CorrelationId = Guid.NewGuid().ToString();

                channel.ExchangeDeclare("statistics-exchange", "fanout");
                channel.BasicPublish(exchange: "statistics-exchange", routingKey: "", basicProperties: properties, body: body);
                await Task.Delay(1000, stoppingToken);
            }
        }
    }
}
