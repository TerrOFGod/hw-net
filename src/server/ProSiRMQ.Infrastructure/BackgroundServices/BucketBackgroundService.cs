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
    private readonly FileServerSettings _fileServerSettings;

    public BucketBackgroundService(IAmazonS3 amazonS3, IOptions<FileServerSettings> fileServerSettingsOptions)
    {
        _amazonS3 = amazonS3;
        _fileServerSettings = fileServerSettingsOptions.Value;
    }
    
    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        stoppingToken.ThrowIfCancellationRequested();
        await EnsureCreatedPersistentBucket();
        await EnsureCreatedTemporaryBucket();
    }

    private async Task EnsureCreatedPersistentBucket()
    {
        await EnsureCreatedBucket(_fileServerSettings.PersistenceBucketName);
    }

    private async Task EnsureCreatedTemporaryBucket()
    {
        await EnsureCreatedBucket(_fileServerSettings.TemporaryBucketName);
    }
    
    private async Task EnsureCreatedBucket(string bucketName)
    {
        var bucketExists = await _amazonS3.DoesS3BucketExistAsync(bucketName);
        
        if (bucketExists)
            return;

        var request = new PutBucketRequest
        {
            BucketName = bucketName
        };

        var response = await _amazonS3.PutBucketAsync(request);

        if (response.HttpStatusCode != HttpStatusCode.OK)
            throw new AggregateException("Can't create bucket");
    }
}