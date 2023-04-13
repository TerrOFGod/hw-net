using Microsoft.EntityFrameworkCore;
using ProSiRMQ.DB;
using ProSiRMQ.DB.Repositories;
using ProSiRMQ.Infrastructure.Interfaces;
using ProSiRMQ.Infrastructure.SignalR.Consumers;

namespace ProSiRMQ.Message.Listener.Extensions;

public static class ServicesExtensions
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
    
    public static IServiceCollection AddDI(this IServiceCollection services)
    {
        services.AddScoped<PublishedConsumer>();
        services.AddScoped<IMessageRepository, MessageRepository>();
        return services;
    }
}