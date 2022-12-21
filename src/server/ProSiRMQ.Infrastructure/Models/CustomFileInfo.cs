namespace ProSiRMQ.Infrastructure.Models;

public class CustomFileInfo
{
    public CustomFileInfo(string key) => Key = key;
    public string Key { get; init; }
}