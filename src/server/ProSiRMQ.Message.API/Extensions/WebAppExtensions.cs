using ProSiRMQ.Infrastructure.Extensions;

namespace ProSiRMQ.Message.API.Extensions;

public static class WebAppExtensions
{
    public static WebApplicationBuilder AddServices(this WebApplicationBuilder builder)
    {
        var services = builder.Services;
        var cfg = builder.Configuration;

        services.AddServices(cfg);
        
        return builder;
    }
    
    public static WebApplication AddMiddlewares(this WebApplication app)
    {
        app.UseDevelopment();
        app.UseCors("CorsPolicy");
        app.UseRedirection();
        app.UseCustomAuthorization();
        app.UseMapControllers();
        app.UseMapHub();

        return app;
    }
}