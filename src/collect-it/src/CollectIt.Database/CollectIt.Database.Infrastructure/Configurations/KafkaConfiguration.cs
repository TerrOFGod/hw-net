using CollectIt.Database.Infrastructure.Serializers;
using CollectIt.Database.Infrastructure.Settings;
using Confluent.Kafka;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;

namespace Microsoft.Extensions.DependencyInjection
{
    public static class KafkaConfiguration
    {
        public static IServiceCollection AddKafkaProducer(this IServiceCollection services)
        {
            return services.AddSingleton(sp =>
            {
                var kafkaSettings = sp.GetRequiredService<IOptions<KafkaSettings>>().Value;
                var config = new ProducerConfig { BootstrapServers = kafkaSettings.Host };

                return new ProducerBuilder<Null, int>(config)
                    .SetValueSerializer(new TrafficSerializer())
                    .Build();
            });
        }

        public static IServiceCollection AddKafkaConsumer(this IServiceCollection services)
        {
            return services.AddSingleton(sp =>
            {
                var kafkaSettings = sp.GetRequiredService<IOptions<KafkaSettings>>().Value;

                var config = new ConsumerConfig
                {
                    BootstrapServers = kafkaSettings.Host,
                    GroupId = kafkaSettings.Group,
                    AutoOffsetReset = AutoOffsetReset.Earliest
                };

                return new ConsumerBuilder<Ignore, int>(config)
                    .SetValueDeserializer(new TrafficSerializer())
                    .Build();
            });
        }

        public static IServiceCollection AddKafkaAdminBuilder(this IServiceCollection services)
        {
            services = services.AddSingleton(sp =>
            {
                var kafkaSettings = sp.GetRequiredService<IOptions<KafkaSettings>>().Value;
                var config = new AdminClientConfig { BootstrapServers = kafkaSettings.Host };
                return new AdminClientBuilder(config).Build();
            });
            return services;
        }
    }
}
