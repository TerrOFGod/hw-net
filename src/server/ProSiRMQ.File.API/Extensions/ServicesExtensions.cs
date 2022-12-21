using Amazon;
using Amazon.Extensions.NETCore.Setup;
using Amazon.Runtime;
using Amazon.S3;
using ProSiRMQ.Infrastructure.BackgroundServices;
using ProSiRMQ.Infrastructure.Configs;
using ProSiRMQ.Infrastructure.Extensions;
using ProSiRMQ.Infrastructure.Interfaces;
using ProSiRMQ.Infrastructure.Services;

namespace ProSiRMQ.File.API.Extensions;

public static class ServicesExtensions
{

    public static IServiceCollection AddFileProvider(this IServiceCollection services)
    {
        services.AddAWSService<IAmazonS3>();
        services.AddScoped<IFileProvider, FileProvider>();
        return services;
    }

    public static IServiceCollection AddCorsPolicy(this IServiceCollection services)
    {
        services.AddCors(o =>
            o.AddPolicy("FileApi", builder =>
            {
                builder
                    .WithOrigins("http://localhost:8080")
                    .AllowAnyMethod()
                    .AllowAnyHeader()
                    .AllowCredentials();
            }));
        return services;
    }
    
    public static IServiceCollection AddAws(this IServiceCollection services, IConfiguration configuration)
    {
        var settings = configuration.Get<FileServerSettings>(FileServerSettings.SectionName);
        services.Configure<FileServerSettings>(configuration, FileServerSettings.SectionName);
        Environment.SetEnvironmentVariable("AWS_ENABLE_ENDPOINT_DISCOVERY", "false");
        Environment.SetEnvironmentVariable("endpoint_discovery_enabled", "false");
        var options = new AWSOptions
        {
            Credentials = new BasicAWSCredentials(settings.User//"minio_user"
                , settings.Password),//"minio_password"),
            DefaultClientConfig =
            {
                ServiceURL = settings.Host//"http://minio:9000"
            },
            Region = RegionEndpoint.EUWest1
        };
            
        services.AddDefaultAWSOptions(options);
        var credentials = new BasicAWSCredentials(settings.User //"minio_user"
            , settings.Password);//"minio_password");
        var config = new AmazonS3Config
        {
            RegionEndpoint = RegionEndpoint.USEast1,
            ForcePathStyle = true,
            ServiceURL = settings.Host//"http://minio:9000"
        };
        var client = new AmazonS3Client(credentials, config);
        services.AddSingleton<IAmazonS3>(client);
        services.AddHostedService<BucketBackgroundService>();
        return services;
    }
}