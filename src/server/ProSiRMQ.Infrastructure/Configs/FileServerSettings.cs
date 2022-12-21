using Microsoft.Extensions.Configuration;

namespace ProSiRMQ.Infrastructure.Configs;

public class FileServerSettings
{
    public const string SectionName = "S3";

    [ConfigurationKeyName("PASSWORD")]
    public string Password { get; set; } = default!;

    [ConfigurationKeyName("USER")]
    public string User { get; set; } = default!;

    [ConfigurationKeyName("HOST")]
    public string Host { get; set; } = default!;

    [ConfigurationKeyName("BUCKET")] 
    public string Bucket { get; set; } = default!;
}