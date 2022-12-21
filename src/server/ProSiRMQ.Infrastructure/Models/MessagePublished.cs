namespace ProSiRMQ.Infrastructure.Models;

public class MessagePublished
{
    public string Sender { get; set; }
    public string Content { get; set; }
    public Guid? FileKey { get; set; }
}