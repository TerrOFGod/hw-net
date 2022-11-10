namespace ProSiRMQ.Infrastructure.Models;

public class Message
{
    public Message(Guid id, DateTime published, string? sender, string content)
    {
        Content = content ?? throw new ArgumentNullException(nameof(content));
        Published = published;
        Sender = sender;
        Id = id;
    }
    
    public Message(DateTime publishDate, string? username, string content)
        : this(Guid.NewGuid(), publishDate, username, content) 
    { }
    
    public Message(string? sender, string content)
    {
        Content = content ?? throw new ArgumentNullException(nameof(content));
        Sender = sender;
    }

    public Guid Id { get; }
    public DateTime Published { get; }
    public string? Sender { get; }
    public string Content { get; }
}