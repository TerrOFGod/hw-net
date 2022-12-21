using ProSiRMQ.Infrastructure.Models;

namespace ProSiRMQ.Infrastructure.Dto;

public class ReadMessageDto
{
    public string Sender { get; set; }
    public string Content { get; set; }
    public string? FileKey { get; set; }
    
    public static ReadMessageDto FromMessage(Message message) => 
        new()
        {
            Sender = message.Sender!,
            Content = message.Content,
            FileKey = message.FileKey.ToString()
        };
}