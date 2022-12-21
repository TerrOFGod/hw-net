using Amazon.Runtime;

namespace ProSiRMQ.Infrastructure.Extensions;

public static class PutObjectResponseExtensions
{
    public static bool IsSuccess(this AmazonWebServiceResponse response)
    {
        return (int) response.HttpStatusCode 
            is >= 200 
            and <= 299;
    }
}