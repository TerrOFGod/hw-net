namespace CollectIt.Database.Infrastructure.Settings
{
    public class KafkaSettings
    {
        public const string SectionName = "Kafka";

        public string Host { get; set; }
        public string Topic { get; set; }
        public string Group { get; set; }
    }
}
