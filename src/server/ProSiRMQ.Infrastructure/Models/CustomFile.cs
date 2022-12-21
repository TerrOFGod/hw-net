namespace ProSiRMQ.Infrastructure.Models;

public class CustomFile
{
    public string Name { get; set; } = "";
    public Stream Body { get; set; } = null!;
    public string ContentType { get; set; } = default!;
}