namespace ProSiRMQ.Infrastructure.Events;

public class MetadataUploaded
{
    public Guid RequestId { get; set; }
    public Dictionary<string, string> Metadata { get; set; }
}