using CollectIt.Api.GraphQL.GraphQL.SchemaTypes;
using CollectIt.Database.Abstractions.Resources;
using CollectIt.Database.Infrastructure;
using System.Security.Policy;

namespace CollectIt.Api.GraphQL.GraphQL
{
    public class Queries
    {
        [UseProjection]
        [UseFiltering]
        public IQueryable<ImageGQLType> ReadImages([Service] PostgresqlCollectItDbContext context)
        {
            return context.Images.Select(i => new ImageGQLType()
            {
                Id = i.Id,
                OwnerId = i.OwnerId,
                Name = i.Name,
                FileName = i.FileName,
                Extension = i.Extension,
                Tags = i.Tags,
                UploadDate = i.UploadDate,
            });
        }

        [UsePaging]
        [UseProjection]
        [UseFiltering]
        public IQueryable<ImageGQLType> ReadImagesPaged([Service] PostgresqlCollectItDbContext context)
        {
            return context.Images.Select(i => new ImageGQLType()
            {
                Id = i.Id,
                OwnerId = i.OwnerId,
                Name = i.Name,
                FileName = i.FileName,
                Extension = i.Extension,
                Tags = i.Tags,
                UploadDate = i.UploadDate,
            });
        }

        [UseProjection]
        [UseFiltering]
        public IQueryable<VideoGQLType> ReadVideos([Service] PostgresqlCollectItDbContext context)
        {
            return context.Videos.Select(i => new VideoGQLType()
            {
                Id = i.Id,
                Duration = i.Duration,
                OwnerId = i.OwnerId,
                Name = i.Name,
                FileName = i.FileName,
                Extension = i.Extension,
                Tags = i.Tags,
                UploadDate = i.UploadDate
            });
        }

        [UsePaging]
        [UseProjection]
        [UseFiltering]
        public IQueryable<VideoGQLType> ReadVideosPaged([Service] PostgresqlCollectItDbContext context)
        {
            return context.Videos.Select(i => new VideoGQLType()
            {
                Id = i.Id,
                Duration = i.Duration,
                OwnerId = i.OwnerId,
                Name = i.Name,
                FileName = i.FileName,
                Extension = i.Extension,
                Tags = i.Tags,
                UploadDate = i.UploadDate
            });
        }

        [UseProjection]
        [UseFiltering]
        public IQueryable<MusicGQLType> ReadMusic([Service] PostgresqlCollectItDbContext context)
        {
            return context.Musics.Select(i => new MusicGQLType()
            {
                Id = i.Id,
                Duration = i.Duration,
                OwnerId = i.OwnerId,
                Name = i.Name,
                FileName = i.FileName,
                Extension = i.Extension,
                Tags = i.Tags,
                UploadDate = i.UploadDate
            });
        }

        [UsePaging]
        [UseProjection]
        [UseFiltering]
        public IQueryable<MusicGQLType> ReadMusicPaged([Service] PostgresqlCollectItDbContext context)
        {
            return context.Musics.Select(i => new MusicGQLType()
            {
                Id = i.Id,
                Duration = i.Duration,
                OwnerId = i.OwnerId,
                Name = i.Name,
                FileName = i.FileName,
                Extension = i.Extension,
                Tags = i.Tags,
                UploadDate = i.UploadDate
            });
        }
    }
}
