using CollectIt.Api.GraphQL.GraphQL;
using CollectIt.Database.Abstractions.Resources;
using CollectIt.Database.Infrastructure;
using CollectIt.Database.Infrastructure.Resources.Managers;
using Microsoft.EntityFrameworkCore;

namespace CollectIt.Api.GraphQL
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            // Add services to the container.

            builder.Services.AddControllers();
            // Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
            builder.Services.AddEndpointsApiExplorer();
            builder.Services.AddSwaggerGen();
            builder.Services.AddDbContext<PostgresqlCollectItDbContext>(options =>
            {
                options.UseNpgsql("Host=localhost;User ID=postgres;Password=postgres;Port=5433;Database=postgres;",
                                  config =>
                                  {
                                      config.MigrationsAssembly("CollectIt.Database.Infrastructure");
                                      config.UseNodaTime();
                                  });

                options.UseOpenIddict<int>();
            });

            builder.Services
                .AddGraphQLServer()
                .AddQueryType<Queries>()
                .AddProjections()
                .AddFiltering();
               
            var app = builder.Build();

            // Configure the HTTP request pipeline.
            if (app.Environment.IsDevelopment())
            {
                app.UseSwagger();
                app.UseSwaggerUI();
            }

            app.UseRouting();
            app.UseHttpsRedirection();

            app.UseAuthorization();

            app.MapControllers();
            app.MapGraphQL("/graphql");

            app.Run();
        }
    }
}