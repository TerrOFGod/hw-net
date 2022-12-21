using ProSiRMQ.File.API.Extensions;

var builder = WebApplication.CreateBuilder(args);

builder.AddServices();

var app = builder.Build();

app.AddMiddleware();

app.Run();
