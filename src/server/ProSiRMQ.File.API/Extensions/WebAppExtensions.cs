using ProSiRMQ.Infrastructure.Configs;
using ProSiRMQ.Infrastructure.Interfaces;
using ProSiRMQ.Infrastructure.Services;

namespace ProSiRMQ.File.API.Extensions;

public static class WebAppExtensions
{
    public static WebApplicationBuilder AddServices(this WebApplicationBuilder builder)
    {
        var services = builder.Services;
        var cfg = builder.Configuration;
        //var s3Options = cfg.GetS3FileServiceOptions();
        
        services.AddSwaggerGen();
        services.AddCorsPolicy();
        services.AddControllers();
        //services.AddSingleton(s3Options);
        services.AddAws(cfg);
        services.AddScoped<IFileProvider, FileProvider>();
        //services.AddFileProvider();
        return builder;
    }
    
    public static WebApplication AddMiddleware(this WebApplication app)
    {
        app.UseSwagger();
        app.UseSwaggerUI();
        app.UseCors("FileApi");
        app.MapControllers();
        return app;
    }
}