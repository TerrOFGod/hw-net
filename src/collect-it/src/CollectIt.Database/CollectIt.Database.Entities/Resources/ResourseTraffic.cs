namespace CollectIt.Database.Entities.Resources
{
    public sealed class ResourseTraffic
    {
        public Guid Id { get; set; } = Guid.NewGuid();
        public int ResourceId { get; set; }
        public DateTimeOffset Time { get; set; } = DateTimeOffset.UtcNow;
    }
}
