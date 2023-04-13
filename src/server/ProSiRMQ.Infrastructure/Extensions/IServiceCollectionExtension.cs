using Amazon;
using Amazon.Extensions.NETCore.Setup;
using Amazon.Runtime;
using Amazon.S3;
using MassTransit;
using MassTransit.Caching;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using MongoDB.Driver;
using ProSiRMQ.Infrastructure.BackgroundServices;
using ProSiRMQ.Infrastructure.Configs;
using ProSiRMQ.Infrastructure.Events;
using ProSiRMQ.Infrastructure.Interfaces;
using ProSiRMQ.Infrastructure.Repositories;
using ProSiRMQ.Infrastructure.Services;
using ProSiRMQ.Infrastructure.SignalR.Consumers;
using StackExchange.Redis;
using CacheSettings = ProSiRMQ.Infrastructure.Configs.CacheSettings;

namespace ProSiRMQ.Infrastructure.Extensions;

public static class IServiceCollectionExtension
{
    public static IServiceCollection AddPublisher(this IServiceCollection services, IConfiguration configuration)
    {
        services.AddMassTransit(config =>
        {
            var host = configuration["RABBITMQ_HOST"];
            if (host is null)
                throw new ArgumentNullException(nameof(host), "Host for RabbitMq is not provided");

            config.UsingRabbitMq((registrationContext, factory) =>
            {
                factory.Host(host, "/", h =>
                {
                    h.Username("guest");
                    h.Password("guest");
                });
                factory.ReceiveEndpoint(e =>
                {
                    e.Bind("amq.fanout");
                });
                factory.ConfigureEndpoints(registrationContext);
            });
        });
        return services;
    }
    
    public static IServiceCollection AddMassageConsumer(this IServiceCollection services, IConfiguration configuration)
    {
        services.AddMassTransit(config =>
        {
            var host = configuration["RABBITMQ_HOST"];
            if (host is null)
                throw new ArgumentNullException(nameof(host), "Host for RabbitMq is not provided");

            config.AddConsumer<PublishedConsumer>();
            config.UsingRabbitMq((registrationContext, factory) =>
            {
                factory.Host(host, "/", h =>
                {
                    h.Username("guest");
                    h.Password("guest");
                });
                factory.ReceiveEndpoint(e =>
                {
                    e.Bind("amq.fanout");
                    e.ConfigureConsumer<PublishedConsumer>(registrationContext);
                });
                factory.ConfigureEndpoints(registrationContext);
            });
        });
        return services;
    }
    
    public static IServiceCollection AddFileMetaConsumer(this IServiceCollection services, IConfiguration configuration)
    {
        services.AddMassTransit(config =>
        {
            var host = configuration["RABBITMQ_HOST"];
            if (host is null)
                throw new ArgumentNullException(nameof(host), "Host for RabbitMq is not provided");

            config.AddConsumer<FileMetadataConsumer>();

            config.UsingRabbitMq((ctx, brokerConfigurator) =>
            {
                brokerConfigurator.Host(host, "/", hostConfigurator =>
                {
                    hostConfigurator.Password("guest");
                    hostConfigurator.Username("guest");
                    
                });
                
                brokerConfigurator.ConfigureEndpoints(ctx);
            });
        });

        return services;
    }
    
    public static IServiceCollection AddFileConsumer(this IServiceCollection services, IConfiguration configuration)
    {
        services.AddMassTransit(config =>
        {
            var host = configuration["RABBITMQ_HOST"];
            if (host is null)
                throw new ArgumentNullException(nameof(host), "Host for RabbitMq is not provided");

            config.AddConsumer<FileUploadedConsumer>();

            config.UsingRabbitMq((ctx, brokerConfigurator) =>
            {
                brokerConfigurator.Host(host, "/", hostConfigurator =>
                {
                    hostConfigurator.Password("guest");
                    hostConfigurator.Username("guest");
                });
                
                brokerConfigurator.ReceiveEndpoint(e =>
                {
                    e.Bind("amq.fanout");
                });
                
                brokerConfigurator.ConfigureEndpoints(ctx);
            });
        });

        return services;
    }
    
    public static IServiceCollection AddAws(this IServiceCollection services, IConfiguration configuration)
    {
        var settings = configuration.Get<FileServerSettings>(FileServerSettings.SectionName);
        services.Configure<FileServerSettings>(configuration, FileServerSettings.SectionName);
        Environment.SetEnvironmentVariable("AWS_ENABLE_ENDPOINT_DISCOVERY", "false");
        Environment.SetEnvironmentVariable("endpoint_discovery_enabled", "false");
        var options = new AWSOptions
        {
            Credentials = new BasicAWSCredentials(settings.User//"minio_user"
                , settings.Password),//"minio_password"),
            DefaultClientConfig =
            {
                ServiceURL = settings.Host//"http://minio:9000"
            },
            Region = RegionEndpoint.EUWest1
        };
            
        services.AddDefaultAWSOptions(options);
        var credentials = new BasicAWSCredentials(settings.User //"minio_user"
            , settings.Password);//"minio_password");
        var config = new AmazonS3Config
        {
            RegionEndpoint = RegionEndpoint.USEast1,
            ForcePathStyle = true,
            ServiceURL = settings.Host//"http://minio:9000"
        };
        var client = new AmazonS3Client(credentials, config);
        services.AddSingleton<IAmazonS3>(client);
        services.AddHostedService<BucketBackgroundService>();
        return services;
    }
    
    public static IServiceCollection AddCache(this IServiceCollection services, IConfiguration configuration)
    {
        var settings = configuration.GetSettings<CacheSettings>();
        var options = new ConfigurationOptions
        {
            EndPoints = new EndPointCollection
            {
                { settings.Host, settings.Port }
            },
            Password = settings.Password
        };

        var redis = ConnectionMultiplexer.Connect(options);
        services.AddScoped(_ => redis.GetDatabase());
        return services;
    }
    
    public static IServiceCollection AddMetadataDatabase(this IServiceCollection services, IConfiguration configuration)
    {
        var client = 
            new MongoClient(
                $@"mongodb://{configuration["MONGO_USERNAME"]}:{configuration["MONGO_PASSWORD"]}@{configuration["MONGO_HOST"]}:{configuration["MONGO_PORT"]}");
        services.AddSingleton<IMongoClient>(client);
        services.AddScoped<IMongoDatabase>(sp => sp
            .GetRequiredService<IMongoClient>()
            .GetDatabase(configuration["MONGO_DATABASE"]));

        return services;
    }
    
    public static IServiceCollection AddMetadataRepo(this IServiceCollection services)
    {
        services.AddScoped<IMetadataRepository, MetadataRepository>();
        return services;
    }
    
    public static IServiceCollection AddCacheService(this IServiceCollection services)
    {
        services.AddScoped<ICacheService, CacheService>();
        return services;
    }
    
    public static IServiceCollection AddFileMover(this IServiceCollection services)
    {
        services.AddScoped<IFileMover, FileMover>();
        return services;
    }
    
    public static IServiceCollection AddFileProvider(this IServiceCollection services)
    {
        services.AddScoped<IFileProvider, FileProvider>();
        return services;
    }
    
    public static IServiceCollection AddCustomSignalR(this IServiceCollection services)
    {
        services.AddSignalR(options => { options.EnableDetailedErrors = true; });
        return services;
    }
    
    public static IServiceCollection AddCorsPolicy(this IServiceCollection services)
    {
        services.AddCors(o =>
            o.AddPolicy("CorsPolicy", builder =>
            {
                builder
                    .WithOrigins("http://localhost:8080")
                    .AllowAnyMethod()
                    .AllowAnyHeader()
                    .AllowCredentials();
            }));
        return services;
    }

    public static IServiceCollection AddSwagger(this IServiceCollection services)
    {
        services.AddSwagger();
        return services;
    }
    
    public static IServiceCollection Configure<T>(this IServiceCollection services, 
        IConfiguration configuration,
        string position) 
        where T : class, new()
    {
        var section = configuration.GetSection(position);
        services.Configure<T>(section);
        return services;
    }
}