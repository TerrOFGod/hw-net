using ProSiRMQ.Infrastructure.Dto;
using ProSiRMQ.Infrastructure.Events;

namespace ProSiRMQ.Infrastructure.Mappers;

public static class FileMapper
{
    public static FileUploaded ToFileUploaded(this FileDto dto, Guid key) => new()
    {
        RequestId = dto.RequestId,
        FileKey = key
    };
}