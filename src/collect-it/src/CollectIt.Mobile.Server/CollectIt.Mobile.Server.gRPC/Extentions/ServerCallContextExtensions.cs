using System.Security.Claims;
using Grpc.Core;

namespace CollectIt.Mobile.Server.gRPC.Extentions;

public static class ServerCallContextExtensions
{
    public static string GetUsername(this ServerCallContext context)
        => context.GetHttpContext().User.FindFirstValue(ClaimTypes.Name)!;

    public static bool IsAdmin(this ServerCallContext context)
        => context.GetUsername().Contains("Admin");
}