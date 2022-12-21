using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.Hosting;
using ProSiRMQ.Infrastructure.SignalR.Hubs;

namespace ProSiRMQ.Infrastructure.Extensions;

public static class MiddlewareExtensions
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
}