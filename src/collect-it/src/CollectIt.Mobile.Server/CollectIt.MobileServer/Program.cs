using CollectIt.Database.Entities.Account;
using CollectIt.Database.Infrastructure;
using CollectIt.Database.Infrastructure.Account.Data;
using CollectIt.MobileServer.GQL;
using CollectIt.MVC.Infrastructure.Account;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Options;
using Microsoft.EntityFrameworkCore;
using CollectIt.MobileServer.Services;
using Microsoft.OpenApi.Models;

namespace CollectIt.MobileServer
{
    public static class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            builder.Services.AddGrpc();
            builder.Services.AddEndpointsApiExplorer();
            builder.Services.AddSwaggerGen();
            
            builder.Services.AddCors(options => options
                .AddDefaultPolicy(c => c
                    .AllowAnyHeader()
                    .AllowAnyMethod()
                    .AllowCredentials()
                    .WithOrigins("http://localhost:3000")));
            
            builder.Services.AddCors(o =>
                o.AddPolicy("grpc-cors-policy", builder =>
                {
                    builder
                        .AllowAnyOrigin()
                        .AllowAnyMethod()
                        .AllowAnyHeader()
                        .WithExposedHeaders("Grpc-Status", "Grpc-Message", "Grpc-Encoding", "Grpc-Accept-Encoding");
                }));

            builder.Services
                .AddGraphQLServer()
                .AddQueryType<Queries>()
                .AddProjections()
                .AddFiltering();

            builder.Services.AddControllers();

            builder.Services.AddEndpointsApiExplorer();
            builder.Services.AddSwaggerGen();
            builder.Services.AddDbContext<PostgresqlCollectItDbContext>(options =>
            {
                options.UseNpgsql(builder.Configuration.GetConnectionString("Postgresql"),
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

            app.UseGrpcWeb();
            
            if (app.Environment.IsDevelopment())
            {
                app.UseSwagger();
                app.UseSwaggerUI();
            }

            app.UseCors();

            app.UseRouting();

            app.MapBananaCakePop();

            app.UseAuthorization();

            app.MapGrpcService<ChatService>()
                .EnableGrpcWeb()
                .RequireCors("grpc-cors-policy");

            app.MapControllers();
            app.MapGraphQL("/graphql");

            app.Run();
        }
    }
}