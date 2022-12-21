using ProSiRMQ.Message.Listener.Extensions;

await Host
    .CreateDefaultBuilder(args)
    .AddServices()
    .Build()
    .RunAsync();