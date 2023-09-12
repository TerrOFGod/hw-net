using CollectIt.Mobile.Server.gRPC.Services;

var builder = WebApplication.CreateBuilder(args);
var services = builder.Services;

// Additional configuration is required to successfully run gRPC on macOS.
// For instructions on how to configure Kestrel and gRPC clients on macOS, visit https://go.microsoft.com/fwlink/?linkid=2099682

// Add services to the container.
services.AddGrpc();

services.AddCors(options => options.AddPolicy("grpc-cors-policy", corsPolicyBuilder =>
{
    corsPolicyBuilder
        .AllowAnyOrigin()
        .AllowAnyMethod()
        .AllowAnyHeader()
        .WithExposedHeaders("Grpc-Status", "Grpc-Message", "Grpc-Encoding", "Grpc-Accept-Encoding");;
}));

var app = builder.Build();
app.UseRouting();

app.UseGrpcWeb();

app.UseCors();

app.MapGrpcService<GrpcChatService>()
    .EnableGrpcWeb()
    .RequireCors("grpc-cors-policy");

app.Run();