using CollectIt.Database.Entities.Resources;
using CollectIt.Database.Infrastructure.Settings;
using Confluent.Kafka;
using Confluent.Kafka.Admin;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using MongoDB.Driver;

namespace Consumer
{
    public sealed class RegisterResourceTrafficConsumer : BackgroundService
    {
        private readonly KafkaSettings _kafkaSettings;
        private readonly IConsumer<Ignore, Guid> _consumer;
        private readonly ILogger<RegisterResourceTrafficConsumer> _logger;
        private readonly IAdminClient _adminClient;
        private readonly IMongoClient _mongoClient;
        private readonly MongoSettings _mongoSettings;

        public RegisterResourceTrafficConsumer(
            IOptions<KafkaSettings> kafkaSettings,
            IConsumer<Ignore, Guid> consumer,
            ILogger<RegisterResourceTrafficConsumer> logger,
            IAdminClient adminClient,
            IMongoClient mongoClient,
            IOptions<MongoSettings> mongoSettings)
        {
            _consumer = consumer;
            _logger = logger;
            _adminClient = adminClient;
            _mongoClient = mongoClient;
            _mongoSettings = mongoSettings.Value;
            _kafkaSettings = kafkaSettings.Value;
        }

        protected override async Task ExecuteAsync(CancellationToken stoppingToken)
        {
            try
            {
                await CreateTopicAsync(stoppingToken);

                _consumer.Subscribe(_kafkaSettings.Topic);

                while (!stoppingToken.IsCancellationRequested)
                {
                    var consumeResult = _consumer.Consume(stoppingToken);
                    var message = consumeResult.Message.Value;

                    var mongoDatabase = _mongoClient.GetDatabase(_mongoSettings.DatabaseName);
                    var collection = mongoDatabase.GetCollection<ResourseTraffic>(_mongoSettings.ResourceTrafficCollectionName);

                    var traffic = new ResourseTraffic { ResourceId = message };
                    await collection.InsertOneAsync(traffic, cancellationToken: stoppingToken);
                }
            }
            catch (Exception)
            {
                _consumer.Close();
                throw;
            }
        }

        private async Task CreateTopicAsync(CancellationToken stoppingToken)
        {
            while (true)
            {
                try
                {
                    await _adminClient.CreateTopicsAsync(new List<TopicSpecification>
                {
                    new() { Name = _kafkaSettings.Topic }
                });
                    break;
                }
                catch (CreateTopicsException ex)
                {
                    if (ex.Results.Any(r => r.Error.Code == ErrorCode.TopicAlreadyExists))
                    {
                        break;
                    }

                    _logger.LogWarning("Try create topic. Retry");
                    await Task.Delay(1000, stoppingToken);
                }
            }
        }
    }
}
