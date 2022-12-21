using MassTransit;
using Microsoft.EntityFrameworkCore;
using ProSiRMQ.DB;
using ProSiRMQ.DB.Repositories;
using ProSiRMQ.Infrastructure.Extensions;
using ProSiRMQ.Infrastructure.Services;
using ProSiRMQ.Infrastructure.SignalR.Consumers;

namespace ProSiRMQ.Message.Listener.Extensions;

public static class ApiServiceCollectionExtensions
{
    public static IHostBuilder AddServices(this IHostBuilder host)
    {
        host.ConfigureServices((ctx, services) =>
        {
            var configuration = ctx.Configuration;
            services.AddDbContext(configuration);
            services.AddDI();
            services.AddConsumer(configuration);
        });

        return host;
    }
}