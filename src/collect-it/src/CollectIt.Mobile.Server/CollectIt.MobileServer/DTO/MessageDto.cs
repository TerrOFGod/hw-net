namespace CollectIt.MobileServer.DTO;

public class MessageDto
{
    public string Username { get; }
    public string Text { get; }

    public MessageDto(string username, string text)
    {
        Username = username;
        Text = text;
    }
}