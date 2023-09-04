using System.ComponentModel.DataAnnotations;

namespace CollectIt.MobileServer.GQL.SchemaTypes
{
    public class ResourceGQLType
    {
        [Key]
        public int Id { get; set; }
        [Required]
        public int OwnerId { get; set; }
        [Required]
        public string Name { get; set; }
        [Required]
        public string FileName { get; set; }
        [Required]
        public string Extension { get; set; }
        public string[] Tags { get; set; }
        [Required]
        public DateTime UploadDate { get; set; }
    }
}
