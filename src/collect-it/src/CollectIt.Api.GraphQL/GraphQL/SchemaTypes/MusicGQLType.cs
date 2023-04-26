using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace CollectIt.Api.GraphQL.GraphQL.SchemaTypes
{

    [Table("Musics")]
    public class MusicGQLType : ResourceGQLType
    {
        [Required]
        [Range(0, int.MaxValue)]
        public int Duration { get; set; }
    }
}
