using Microsoft.EntityFrameworkCore;
using ProSiRMQ.DB.Models;

namespace ProSiRMQ.DB;

public class AppDbContext: DbContext
{
    public DbSet<Message> Messages => Set<Message>();

    public AppDbContext(DbContextOptions<AppDbContext> options)
        : base(options)
    {
        Database.EnsureCreated();
    }
}