namespace ProSiRMQ.DB.Models;

public class Message
{
    public DateTime Published { get; set; }
    public Guid Id { get; set; }
    public string? Sender { get; set; }
    public string Content { get; set; }
}