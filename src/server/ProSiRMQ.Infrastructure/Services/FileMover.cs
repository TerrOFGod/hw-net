﻿using Amazon.S3;
using Microsoft.Extensions.Options;
using ProSiRMQ.Infrastructure.Configs;
using ProSiRMQ.Infrastructure.Extensions;
using ProSiRMQ.Infrastructure.Interfaces;

namespace ProSiRMQ.Infrastructure.Services;

public class FileMover : IFileMover
{
    private readonly IAmazonS3 _amazonS3;
    private readonly FileServerSettings _fileServerSettings;

    public FileMover(IAmazonS3 amazonS3, IOptions<FileServerSettings> fileServerSettingsOptions)
    {
        _amazonS3 = amazonS3;
        _fileServerSettings = fileServerSettingsOptions.Value;
    }

    public async Task<bool> MoveToPersistenceAsync(Guid fileKey)
    {
        var response = await _amazonS3.CopyObjectAsync(
            _fileServerSettings.TemporaryBucketName, fileKey.ToString(),
            _fileServerSettings.PersistenceBucketName, fileKey.ToString());

        if (!response.IsSuccess())
        {
            return false;
        }
        
        await _amazonS3.DeleteObjectAsync(_fileServerSettings.TemporaryBucketName, fileKey.ToString());

        return true;
    }
}