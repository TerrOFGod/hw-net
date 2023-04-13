using ProSiRMQ.Infrastructure.Extensions;
using ProSiRMQ.Infrastructure.SignalR.Consumers;

await Host
    .CreateDefaultBuilder(args)
    .ConfigureServices((ctx, services) =>
    {
        var configuration = ctx.Configuration;

        services.AddMetadataRepo();
        services.AddFileMover();
        services.AddAws(configuration);
        services.AddFileMetaConsumer(configuration);
        services.AddCache(configuration);
        services.AddCacheService();
        services.AddMetadataDatabase(configuration);
    })
    .Build()
    .RunAsync();