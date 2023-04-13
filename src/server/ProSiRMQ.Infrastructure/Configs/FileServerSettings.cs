using Microsoft.Extensions.Configuration;
using ProSiRMQ.Infrastructure.Interfaces;

namespace ProSiRMQ.Infrastructure.Configs;

public class FileServerSettings : ISettings
{
    public static string SectionName => "S3";

    [ConfigurationKeyName("PASSWORD")]
    public string Password { get; set; } = default!;

    [ConfigurationKeyName("USER")]
    public string User { get; set; } = default!;

    [ConfigurationKeyName("HOST")]
    public string Host { get; set; } = default!;

    [ConfigurationKeyName("BUCKET")] 
    public string Bucket { get; set; } = default!;
    
    [ConfigurationKeyName("TEMPORARY")] 
    public string TemporaryBucketName { get; set; } = default!;
    
    [ConfigurationKeyName("PERSISTENT")] 
    public string PersistenceBucketName { get; set; } = default!;
}