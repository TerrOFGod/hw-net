using CollectIt.Database.Infrastructure.Settings;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;
using MongoDB.Driver;

namespace Microsoft.Extensions.DependencyInjection
{
    public static class MongoConfiguration
    {
        public static IServiceCollection AddMongoDb(this IServiceCollection services)
        {
            services.AddSingleton<IMongoClient>(sp =>
            {
                var settings = sp.GetRequiredService<IOptions<MongoSettings>>();
                return new MongoClient(settings.Value.ConnectionString);
            });

            return services;
        }
    }
}
