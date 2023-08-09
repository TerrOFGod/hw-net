using System.Collections.Concurrent;

namespace CollectIt.Mobile.Server.gRPC.Dtos;

public class UserDto
{
    public string Username { get; }
    public ConcurrentQueue<MessageDto> Messages { get; } = new();

    public UserDto(string username)
    {
        Username = username;
    }
}