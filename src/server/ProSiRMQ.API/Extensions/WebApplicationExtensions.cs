using ProSiRMQ.Infrastructure.SignalR.Hubs;

namespace Microsoft.AspNetCore.Builder;

public static class WebApplicationExtensions
{
    public static WebApplication UseDevelopment(this WebApplication app)
    {
        if (!app.Environment.IsDevelopment()) return app;
        app.UseSwagger();
        app.UseSwaggerUI();
        return app;
    }
    
    public static WebApplication UseCors(this WebApplication app)
    {
        app.UseCors("CorsPolicy");
        return app;
    }
    
    public static WebApplication UseRedirection(this WebApplication app)
    {
        app.UseHttpsRedirection();
        return app;
    }
    
    public static WebApplication UseCustomAuthorization(this WebApplication app)
    {
        app.UseAuthorization();
        return app;
    }
    
    public static WebApplication UseMapControllers(this WebApplication app)
    {
        app.MapControllers();
        return app;
    }
    
    public static WebApplication UseMapHub(this WebApplication app)
    {
        app.MapHub<ChatHub>("/chat");
        return app;
    }

    public static WebApplication AddMiddlewares(this WebApplication app)
    {
        app.UseDevelopment()
            .UseCors()
            .UseRedirection()
            .UseCustomAuthorization()
            .UseMapControllers()
            .UseMapHub();

        return app;
    }
}