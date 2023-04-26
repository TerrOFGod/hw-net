using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace CollectIt.Api.GraphQL.GraphQL.SchemaTypes
{
    [Table("Videos")]
    public class VideoGQLType : ResourceGQLType
    {
        [Required]
        [Range(0, int.MaxValue)]
        public int Duration { get; set; }
    }
}
