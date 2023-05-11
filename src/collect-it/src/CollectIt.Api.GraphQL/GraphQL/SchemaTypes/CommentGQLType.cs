using CollectIt.Database.Entities.Account;
using CollectIt.Database.Entities.Resources;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace CollectIt.Api.GraphQL.GraphQL.SchemaTypes
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
