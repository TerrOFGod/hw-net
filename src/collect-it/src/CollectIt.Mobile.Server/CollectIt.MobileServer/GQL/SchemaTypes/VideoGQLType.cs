using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace CollectIt.MobileServer.GQL.SchemaTypes
{
    [Table("Videos")]
    public class VideoGQLType : ResourceGQLType
    {
        [Required]
        [Range(0, int.MaxValue)]
        public int Duration { get; set; }
    }
}
