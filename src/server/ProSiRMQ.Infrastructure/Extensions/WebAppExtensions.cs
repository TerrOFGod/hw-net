using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.DependencyInjection;
using ProSiRMQ.Infrastructure.Configs;

namespace ProSiRMQ.Infrastructure.Extensions;

public static class WebAppExtensions
{
    public static WebApplicationBuilder AddServices(this WebApplicationBuilder builder)
    {
        var services = builder.Services;
        var cfg = builder.Configuration;
        //var s3Options = cfg.GetS3FileServiceOptions();
        
        services.AddSwagger();
        services.AddControllers();
        //services.AddSingleton(s3Options);
        //services.AddAws(cfg);
        services.AddFileProvider();
        return builder;
    }
    
    public static WebApplication AddMiddleware(this WebApplication app)
    {
        app.UseSwagger();
        app.UseSwaggerUI();
        app.MapControllers();
        return app;
    }
}