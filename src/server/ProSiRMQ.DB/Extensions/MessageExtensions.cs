using ProSiRMQ.Infrastructure.Models;

namespace ProSiRMQ.DB.Extensions;

public static class MessageExtensions
{
    public static Models.Message ToModel(this Message message)
    {
        return new Models.Message()
        {
            Id = message.Id,
            Content = message.Content,
            Sender = message.Sender,
            Published = message.Published
        };
    }
}