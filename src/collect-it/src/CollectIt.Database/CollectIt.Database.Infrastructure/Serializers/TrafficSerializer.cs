using Confluent.Kafka;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;

namespace CollectIt.Database.Infrastructure.Serializers
{
    public class TrafficSerializer : IDeserializer<Guid>, ISerializer<Guid>
    {
        public Guid Deserialize(ReadOnlySpan<byte> data, bool isNull, SerializationContext context)
        {
            if (isNull)
            {
                return Guid.Empty;
            }

            var json = System.Text.Encoding.UTF8.GetString(data.ToArray());
            var message = JsonSerializer.Deserialize<Guid>(json);

            return message!;
        }

        public byte[] Serialize(Guid data, SerializationContext context)
        {
            if (data == null)
            {
                return null!;
            }

            var bytes = JsonSerializer.SerializeToUtf8Bytes(data);

            return bytes;
        }
    }
}
