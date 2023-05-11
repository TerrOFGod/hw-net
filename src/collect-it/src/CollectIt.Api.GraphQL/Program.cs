using CollectIt.Api.GraphQL.GraphQL;
using CollectIt.Database.Abstractions.Resources;
using CollectIt.Database.Entities.Account;
using CollectIt.Database.Infrastructure;
using CollectIt.Database.Infrastructure.Account.Data;
using CollectIt.Database.Infrastructure.Resources.Managers;
using CollectIt.MVC.Infrastructure.Account;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;

namespace CollectIt.Api.GraphQL
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);
            
            builder.Services.AddCors(o =>
                o.AddPolicy("CorsPolicy", builder =>
                {
                    builder
                        .AllowAnyOrigin()
                        .AllowAnyMethod()
                        .AllowAnyHeader();
                }));
            
            builder.Services
                .AddGraphQLServer()
                .AddQueryType<Queries>()
                .AddProjections()
                .AddFiltering();

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


            builder.Services.AddIdentity<User, Role>(config =>
            {
                config.User = new UserOptions { RequireUniqueEmail = true, };
                config.Password = new PasswordOptions
                {
                    RequireDigit = true,
                    RequiredLength = 6,
                    RequireLowercase = false,
                    RequireUppercase = false,
                    RequiredUniqueChars = 1,
                    RequireNonAlphanumeric = false,
                };
                config.SignIn = new SignInOptions
                {
                    RequireConfirmedEmail = false,
                    RequireConfirmedAccount = false,
                    RequireConfirmedPhoneNumber = false,
                };
            })
        .AddEntityFrameworkStores<PostgresqlCollectItDbContext>()
        .AddUserManager<UserManager>()
        .AddDefaultTokenProviders()
        .AddErrorDescriber<RussianLanguageIdentityErrorDescriber>();

            var app = builder.Build();


            // Configure the HTTP request pipeline.
            if (app.Environment.IsDevelopment())
            {
                app.UseSwagger();
                app.UseSwaggerUI();
            }

            app.UseCors("CorsPolicy");

            app.UseRouting();


            app.MapBananaCakePop();

            app.UseAuthorization();


            app.MapControllers();
            app.MapGraphQL("/graphql");


            app.Run();
        }
    }
}