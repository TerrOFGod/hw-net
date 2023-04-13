using Microsoft.Extensions.Configuration;
using ProSiRMQ.Infrastructure.Interfaces;

namespace ProSiRMQ.Infrastructure.Extensions;

public static class IConfigurationExtension
{
    public static T Get<T>(this IConfiguration configuration, string position) 
        where T : new()
    {
        var obj = new T();
        
        configuration
            .GetSection(position)
            .Bind(obj);
        
        return obj;
    }
    
    public static TSettings GetSettings<TSettings>(this IConfiguration configuration)
        where TSettings : ISettings
    {
        return configuration
                   .GetRequiredSection(TSettings.SectionName)
                   .Get<TSettings>()
               ?? throw new InvalidOperationException($"Couldn't create setting {nameof(TSettings)} by section name {TSettings.SectionName}");
    }
}