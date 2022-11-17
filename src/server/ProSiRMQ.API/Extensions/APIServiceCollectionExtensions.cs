using MassTransit;
using Microsoft.EntityFrameworkCore;
using ProSiRMQ.DB;
using ProSiRMQ.DB.Repositories;
using ProSiRMQ.Infrastructure.Services;
using ProSiRMQ.Infrastructure.SignalR.Consumers;

namespace ProSiRMQ.API.Extensions;

public static class ApiServiceCollectionExtensions
{
    public static IServiceCollection AddCustomMassTransit(this IServiceCollection services, IConfiguration configuration)
    {
        services.AddMassTransit(config =>
        {
            var host = configuration["RABBITMQ_HOST"];
            if (host is null)
            {
                throw new ArgumentNullException(nameof(host), "Host for RabbitMq is not provided");
            }

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
            // config.AddConsumer<PublishedConsumer>();
            // config.UsingInMemory((context, configurator) =>
            // {
            //     configurator.Host();
            //     configurator.ReceiveEndpoint(new TemporaryEndpointDefinition(), x =>
            //     {
            //         x.ConfigureConsumer<PublishedConsumer>(context);
            //     });
            //     configurator.ConfigureEndpoints(context);
            // });
        });
        return services;
    }
    
    public static IServiceCollection AddDbContext(this IServiceCollection services, IConfiguration configuration)
    {
        services.AddDbContext<AppDbContext>(x =>
        {
            //x.UseNpgsql("User Id=postgres;Password=Vselord2002;Host=localhost;Port=5432;Database=dotnet");
            x.UseNpgsql($"User Id={configuration["DB_USERNAME"]};Password={configuration["DB_PASSWORD"]};Host={configuration["DB_HOST"]};Database={configuration["DB_DATABASE"]};Port={configuration["DB_PORT"]}");
        });
        return services;
    }
    
    public static IServiceCollection AddDI(this IServiceCollection services)
    {
        //services.AddScoped<PublishedConsumer>();
        services.AddScoped<IMessageRepository, MessageRepository>();
        return services;
    }

    public static IServiceCollection AddServices(this IServiceCollection services, IConfiguration configuration)
    {
        services.AddEndpointsApiExplorer();
        services.AddSwaggerGen();
        services.AddCors(o =>
            o.AddPolicy("CorsPolicy", builder =>
            {
                builder
                    .WithOrigins("http://localhost:8080")
                    .AllowAnyMethod()
                    .AllowAnyHeader()
                    .AllowCredentials();
            }));
        services.AddSignalR(options => 
        { 
            options.EnableDetailedErrors = true; 
        });
        services.AddDbContext(configuration)
            .AddDI();
        services.AddSignalR();
        services.AddCustomMassTransit(configuration);

        return services;
    }
}