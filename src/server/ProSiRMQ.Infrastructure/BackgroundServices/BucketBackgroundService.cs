using System.Net;
using Amazon.S3;
using Amazon.S3.Model;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Options;
using ProSiRMQ.Infrastructure.Configs;

namespace ProSiRMQ.Infrastructure.BackgroundServices;

public class BucketBackgroundService : BackgroundService
{
    private readonly IAmazonS3 _amazonS3;
    private readonly FileServerSettings _fileServerConfigs;

    public BucketBackgroundService(IAmazonS3 amazonS3, IOptions<FileServerSettings> fileServerSettingsOptions)
    {
        _amazonS3 = amazonS3;
        _fileServerConfigs = fileServerSettingsOptions.Value;
    }
    
    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        var bucketExists = await _amazonS3.DoesS3BucketExistAsync(_fileServerConfigs.Bucket);//"user-files");
        
        if (bucketExists)
        {
            return;
        }

        await CreateBucketAsync(stoppingToken);
    }
    
    private async Task CreateBucketAsync(CancellationToken token = new())
    {
        var request = new PutBucketRequest
        {
            BucketName = _fileServerConfigs.Bucket,//"user-files",
            CannedACL = S3CannedACL.PublicReadWrite,
            UseClientRegion = true
        };

        var response = await _amazonS3.PutBucketAsync(request, token);

        if (response.HttpStatusCode != HttpStatusCode.OK)
        {
            throw new AggregateException("Can't create bucket");
        }
    }
}