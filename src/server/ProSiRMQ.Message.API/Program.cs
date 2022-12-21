using ProSiRMQ.Message.API.Extensions;

var builder = WebApplication.CreateBuilder(args);

builder.AddServices();

var app = builder.Build();

app.AddMiddlewares();

app.Run();


