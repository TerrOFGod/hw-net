using ProSiRMQ.Infrastructure.Dto;
using ProSiRMQ.Infrastructure.Events;
using ProSiRMQ.Infrastructure.Models;

namespace ProSiRMQ.Infrastructure.Mappers;

public static class MetadataMapper
{
    public static MetadataUploaded ToMetadataUploaded(this MetadataDto dto) => new()
    {
        RequestId = dto.RequestId,
        Metadata = dto.Metadata
    };
}