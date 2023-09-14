using Confluent.Kafka;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;

namespace CollectIt.Database.Infrastructure.Serializers
{
    public class TrafficSerializer : IDeserializer<int>, ISerializer<int>
    {
        public int Deserialize(ReadOnlySpan<byte> data, bool isNull, SerializationContext context)
        {
            if (isNull)
            {
                return -1;
            }

            var json = System.Text.Encoding.UTF8.GetString(data);
            var message = JsonSerializer.Deserialize<int>(json);

            return message!;
        }

        public byte[] Serialize(int data, SerializationContext context)
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
