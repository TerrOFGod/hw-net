using System.Text.Json;
using ProSiRMQ.API.Extensions;
using ProSiRMQ.Infrastructure.Dto;
using ProSiRMQ.Infrastructure.Services;

var builder = WebApplication.CreateBuilder(args);
var cfg = builder.Configuration;
var services = builder.Services;

builder.Services.AddControllers().AddJsonOptions(json =>
{
    json.JsonSerializerOptions.PropertyNamingPolicy = JsonNamingPolicy.CamelCase;
});

services.AddServices(cfg);

//services.AddMassTransitHostedService();

var app = builder.Build();

// Configure the HTTP request pipeline.
app.AddMiddlewares();



app.MapGet("/history/{count}",
    async (HttpContext context, IMessageRepository messageRepository, int count) =>
    {
        var messages = await messageRepository.GetHistory(count);

        if (messages is null)
        {
            context.Response.StatusCode = StatusCodes.Status500InternalServerError;
            return;
        }
        
        var items = messages.Select(ReadMessageDto.FromMessage);
        await context.Response.WriteAsJsonAsync(items);
    });

app.Run();