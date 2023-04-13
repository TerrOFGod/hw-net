using Amazon.S3;
using Amazon.S3.Model;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using ProSiRMQ.Infrastructure.Configs;
using ProSiRMQ.Infrastructure.Extensions;
using ProSiRMQ.Infrastructure.Interfaces;
using ProSiRMQ.Infrastructure.Models;

namespace ProSiRMQ.Infrastructure.Services;

public class FileProvider : IFileProvider
{
    private readonly ILogger<FileProvider> logger;
    private readonly IAmazonS3 _amazonS3;
    private readonly FileServerSettings _fileServerSettings;

    public FileProvider(IAmazonS3 amazonS3, IOptions<FileServerSettings> fileServerSettingsOptions, ILogger<FileProvider> logger)
    {
        _amazonS3 = amazonS3;
        this.logger = logger;
        _fileServerSettings = fileServerSettingsOptions.Value;
    }
    
    public async Task<CustomFile> FindFileAsync(string key, CancellationToken token = new())
    {
        var request =  new GetObjectRequest()
        {
            BucketName = _fileServerSettings.PersistenceBucketName,//"user-files",
            Key = key
        };
        
        var response = await _amazonS3.GetObjectAsync(request, token);
        logger.LogWarning("file found with ct: {ct}", response.Headers.ContentType);
        if (response is null)
            throw new ArgumentNullException("No such file has been found");
        
        var result = new CustomFile
        {
            Body = response.ResponseStream,
            Name = string.Join("-", response.BucketName, response.Key) 
                   + "." + response.Headers.ContentType.Split('/')[1],
            ContentType = response.Headers.ContentType
        };
            
        return result;
    }

    public async Task<CustomFileInfo> SaveFileAsync(IFormFile file, CancellationToken token = new())
    {
        var key = Guid.NewGuid().ToString();
        var stream = file.OpenReadStream();
        var request = new PutObjectRequest
        {
            BucketName = _fileServerSettings.TemporaryBucketName,//"user-files",
            Key = key,
            InputStream = file.OpenReadStream(),
            ContentType = file.ContentType
        };

        var response = await _amazonS3.PutObjectAsync(request, token);
        logger.LogWarning("File maybe saved with ct: {ct1}, {ct2}, {ct3}.", 
            request.ContentType, file.ContentType, file.Headers.ContentType);

        if (!response.IsSuccess())
            throw new BadHttpRequestException(
                $"Request to S3 storage was not success. Status code {response.HttpStatusCode}");
        
        logger.LogWarning("File successfully saved.");
        
        var result = new CustomFileInfo(key);

        return result;
    }
}