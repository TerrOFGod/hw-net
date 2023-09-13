using CollectIt.Database.Infrastructure.Settings;
using Consumer;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;

var host = Host.CreateDefaultBuilder(args)
    .ConfigureServices((ctx, services) =>
    {
        var c = ctx;
        services.Configure<KafkaSettings>(ctx.Configuration.GetSection(KafkaSettings.SectionName));
        services.Configure<MongoSettings>(ctx.Configuration.GetSection(MongoSettings.SectionName));
        services.AddKafkaAdminBuilder();
        services.AddKafkaConsumer();
        services.AddMongoDb();
        services.AddHostedService<RegisterResourceTrafficConsumer>();
    })
    .Build();

host.Run();