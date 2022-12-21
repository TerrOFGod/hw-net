using ProSiRMQ.Infrastructure.Models;

namespace ProSiRMQ.DB.Mappers;

public static class MessageExtensions
{
    public static Models.Message ToDbModel(this Message message)
    {
        return new Models.Message()
        {
            Id = message.Id,
            Content = message.Content,
            Sender = message.Sender,
            Published = message.Published,
            FileKey = message.FileKey
        };
    }
}