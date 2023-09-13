using CollectIt.Database.Infrastructure.Settings;
using MongoDB.Driver.Core.Configuration;
using System.Configuration;

namespace CollectIt.Mobile.Server.ResourcesStatistics
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            builder.Services.AddCors(options =>
                options.AddDefaultPolicy(o =>
                    o.AllowAnyOrigin()
                    .AllowAnyHeader()
                    .AllowAnyMethod()));

            builder.Services.AddControllers();

            builder.Services.AddSwaggerGen();

            builder.Services.Configure<KafkaSettings>(builder.Configuration.GetSection(KafkaSettings.SectionName));
            builder.Services.AddKafkaProducer();

            builder.Services.AddHostedService<StatisticsBackgroundService>();

            builder.Services.Configure<MongoSettings>(builder.Configuration.GetSection(MongoSettings.SectionName));
            builder.Services.AddMongoDb();


            var app = builder.Build();

            if (app.Environment.IsDevelopment())
            {
                app.UseSwagger();
                app.UseSwaggerUI();
            }

            app.UseCors();

            app.UseRouting();

            app.MapControllers();

            app.Run();
        }
    }
}