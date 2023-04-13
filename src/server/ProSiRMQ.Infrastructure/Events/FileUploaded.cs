namespace ProSiRMQ.Infrastructure.Events;

public class FileUploaded
{
    public Guid RequestId { get; set; }
    public Guid FileKey { get; set; }
}