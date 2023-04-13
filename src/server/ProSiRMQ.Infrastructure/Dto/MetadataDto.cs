namespace ProSiRMQ.Infrastructure.Dto;

public class MetadataDto
{
    public Guid RequestId { get; set; }
    public Dictionary<string, string> Metadata { get; set; }
}