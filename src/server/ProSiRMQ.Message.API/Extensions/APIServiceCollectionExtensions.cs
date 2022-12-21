using MassTransit;
using Microsoft.EntityFrameworkCore;
using ProSiRMQ.DB;
using ProSiRMQ.DB.Repositories;
using ProSiRMQ.Infrastructure.Extensions;
using ProSiRMQ.Infrastructure.Interfaces;
using ProSiRMQ.Infrastructure.Services;
using ProSiRMQ.Infrastructure.SignalR.Consumers;

namespace ProSiRMQ.Message.API.Extensions;

public static class ApiServiceCollectionExtensions
{
    public static IServiceCollection AddDbContext(this IServiceCollection services, IConfiguration configuration)
    {
        services.AddDbContext<AppDbContext>(x =>
        {
            //x.UseNpgsql("User Id=postgres;Password=Vselord2002;Host=localhost;Port=5432;Database=dotnet");
            x.UseNpgsql($"User Id={configuration["DB_USERNAME"]};Password={configuration["DB_PASSWORD"]};Host={configuration["DB_HOST"]};Database={configuration["DB_DATABASE"]};Port={configuration["DB_PORT"]}");
        });
        return services;
    }
    
    public static IServiceCollection AddRepos(this IServiceCollection services)
    {
        services.AddScoped<IMessageRepository, MessageRepository>();
        return services;
    }

    public static IServiceCollection AddServices(this IServiceCollection services, IConfiguration configuration)
    {
        services.AddControllers();
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
        services.AddCustomSignalR();
        services.AddDbContext(configuration);
        services.AddRepos();
        services.AddPublisher(configuration);

        return services;
    }
}