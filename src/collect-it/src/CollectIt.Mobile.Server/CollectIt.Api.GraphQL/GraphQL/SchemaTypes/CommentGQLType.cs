using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace CollectIt.Mobile.Server.GraphQL.GraphQL.SchemaTypes
{
    public class CommentGQLType
    {
        [Key]
        public int CommentId { get; set; }

        public int OwnerId { get; set; }

        [Required]
        public string Content { get; set; }

        [Required]
        public DateTime UploadDate { get; set; }

        public int TargetId { get; set; }
        [Required]
        [ForeignKey(nameof(TargetId))]
        public ResourceGQLType Target { get; set; }
    }
}
