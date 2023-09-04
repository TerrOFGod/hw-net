using CollectIt.Database.Entities.Account;
using CollectIt.Database.Infrastructure;
using CollectIt.Database.Infrastructure.Account.Data;
using CollectIt.MobileServer.GQL;
using CollectIt.MVC.Infrastructure.Account;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using CollectIt.MobileServer.Services;

namespace CollectIt.MobileServer
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            builder.Services.AddGrpc();

            builder.Services.AddCors(o =>
                o.AddPolicy("CorsPolicy", builder =>
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

            if (app.Environment.IsDevelopment())
            {
                app.UseSwagger();
                app.UseSwaggerUI();
            }

            app.UseCors("CorsPolicy");

            app.UseRouting();

            app.MapBananaCakePop();

            app.UseAuthorization();

            app.MapGrpcService<ChatService>()
                .RequireCors("grpc-cors-policy");

            app.MapControllers();
            app.MapGraphQL("/graphql");

            app.Run();
        }
    }
}