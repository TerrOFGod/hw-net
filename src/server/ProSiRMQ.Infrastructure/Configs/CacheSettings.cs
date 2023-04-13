using Microsoft.Extensions.Configuration;
using ProSiRMQ.Infrastructure.Interfaces;

namespace ProSiRMQ.Infrastructure.Configs;

public class CacheSettings : ISettings
{
    public static string SectionName => "CACHE";
    [ConfigurationKeyName("HOST")]
    public string Host { get; set; } = default!;

    [ConfigurationKeyName("PORT")]
    public int Port { get; set; } = default!;

    [ConfigurationKeyName("PASSWORD")]
    public string Password { get; set; } = default!;

}