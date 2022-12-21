using Microsoft.Extensions.Configuration;

namespace ProSiRMQ.Infrastructure.Configs;

public class FileServerConfigs
{
    public string Bucket { get; init; } = null!;
    public Uri Host { get; init; } = null!;
    public string Password { get; init; } = null!;
    public string SecretKey { get; init; } = null!;
}

public static class FileServerConfigsExtension
{
    public static FileServerConfigs GetS3FileServiceOptions(this ConfigurationManager manager)
    {
        return new FileServerConfigs()
        {
            Bucket = manager["S3_BUCKET"] ?? throw new ArgumentException("S3_BUCKET env variable is not provided"),
            Host = new Uri(manager["S3_HOST"]) ?? throw new ArgumentException("S3_HOST env variable is not provided"),
            Password = manager["S3_PASSWORD"] ?? throw new ArgumentException("S3_PASSWORD env variable is not provided"),
            SecretKey = manager["S3_SECRET"] ?? throw new ArgumentException("S3_SECRET env variable is not provided")
        };
    }
}