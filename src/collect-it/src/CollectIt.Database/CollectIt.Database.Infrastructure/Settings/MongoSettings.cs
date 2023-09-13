namespace CollectIt.Database.Infrastructure.Settings
{
    public class MongoSettings
    {
        public const string SectionName = "Mongo";

        public string ConnectionString { get; set; }
        public string DatabaseName { get; set; }
        public string ResourceTrafficCollectionName { get; set; }

    }
}
