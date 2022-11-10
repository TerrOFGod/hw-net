using System.Text.Json;
using MassTransit;
using Microsoft.EntityFrameworkCore;
using ProSiRMQ.DB;
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

services.AddMassTransitHostedService();

var app = builder.Build();

// Configure the HTTP request pipeline.
app.AddMiddlewares();

//add for testing in docker compose(doesn't work)
using (var serviceScope = app.Services.CreateScope())
{
    // Takes all of our migrations files and apply them against the database in case they are not implemented
    serviceScope.ServiceProvider.GetService<AppDbContext>()?.Database.Migrate();
}

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