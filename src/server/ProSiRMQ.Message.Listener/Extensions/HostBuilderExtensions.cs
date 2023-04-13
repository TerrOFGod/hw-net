using ProSiRMQ.Infrastructure.Extensions;

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
            services.AddMassageConsumer(configuration);
        });

        return host;
    }
}