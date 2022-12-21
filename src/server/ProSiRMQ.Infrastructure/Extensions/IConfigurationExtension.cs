using Microsoft.Extensions.Configuration;

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
}