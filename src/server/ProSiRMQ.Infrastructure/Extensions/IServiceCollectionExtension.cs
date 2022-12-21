using MassTransit;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using ProSiRMQ.Infrastructure.Interfaces;
using ProSiRMQ.Infrastructure.Services;
using ProSiRMQ.Infrastructure.SignalR.Consumers;

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
    
    public static IServiceCollection AddConsumer(this IServiceCollection services, IConfiguration configuration)
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