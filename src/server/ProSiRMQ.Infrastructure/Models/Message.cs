namespace ProSiRMQ.Infrastructure.Models;

public class Message
{
    public Message(Guid id, DateTime published, string? sender, string content, Guid? key)
    {
        FileKey = key;
        Content = content ?? throw new ArgumentNullException(nameof(content));
        Published = published;
        Sender = sender;
        Id = id;
    }
    
    public Message(DateTime publishDate, string? username, string content, Guid? key)
        : this(Guid.NewGuid(), publishDate, username, content, key) 
    { }

    public Message(string? sender, string content)
    {
        Content = content ?? throw new ArgumentNullException(nameof(content));
        Sender = sender;
    }
    
    public Message(string? sender, string content, Guid? fileKey)
    {
        Content = content ?? throw new ArgumentNullException(nameof(content));
        Sender = sender;
        FileKey = fileKey;
    }

    public Guid Id { get; }
    public DateTime Published { get; }
    public string? Sender { get; }
    public string Content { get; }
    public Guid? FileKey { get; }
}