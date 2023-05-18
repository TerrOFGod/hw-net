package com.example.data.managers

import com.apollographql.apollo3.ApolloClient
import com.example.core.dtos.resources.images.item.ReadImageNode
import com.example.core.dtos.resources.images.list.ReadImagesList
import com.example.core.dtos.resources.music.item.ReadMusicNode
import com.example.core.dtos.resources.music.list.ReadMusicList
import com.example.core.dtos.resources.videos.item.ReadVideoNode
import com.example.core.dtos.resources.videos.list.ReadVideoList
import com.example.core.managers.GraphQLManager
import com.example.data.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

class GraphQLManagerImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : GraphQLManager{
    override suspend fun getImagesList(): ReadImagesList {
        val response = apolloClient.query(ImagesQuery()).execute()
        var responseList = response.data?.readImages ?: emptyList()
        val imagesList = ReadImagesList(mutableListOf())
        responseList.forEach {
            val image = ReadImageNode(
                it.id,
                it.name,
                it.fileName,
                it.extension,
                it.uploadDate,
                it.tags
            )
            imagesList.list.add(image)
        }
        return imagesList
    }

    override suspend fun getImage(id: Int): ReadImageNode {
        val response = apolloClient.query(ImageQuery(id)).execute()
        var responseData = response.data?.readImages ?: emptyList()
        val imagesList = ReadImagesList(mutableListOf())
        responseData.forEach {
            val image = ReadImageNode(
                it.id,
                it.name,
                it.fileName,
                it.extension,
                it.uploadDate,
                it.tags
            )
            imagesList.list.add(image)
        }
        return imagesList.list.first()
    }

    override suspend fun getMusicList(): ReadMusicList {
        val response = apolloClient.query(MusicQuery()).execute()
        var responseList = response.data?.readMusic ?: emptyList()
        val musicList = ReadMusicList(mutableListOf())
        responseList.forEach {
            val music = ReadMusicNode(
                it.id,
                it.name,
                it.fileName,
                it.extension,
                it.uploadDate,
                it.tags
            )
            musicList.list.add(music)
        }
        return musicList
    }

    override suspend fun getMusic(id: Int): ReadMusicNode {
        val response = apolloClient.query(MusicDetailsQuery(id)).execute()
        var responseData = response.data?.readMusic ?: emptyList()
        val musicList = ReadMusicList(mutableListOf())
        responseData.forEach {
            val music = ReadMusicNode(
                it.id,
                it.name,
                it.fileName,
                it.extension,
                it.uploadDate,
                it.tags
            )
            musicList.list.add(music)
        }
        return musicList.list.first()
    }

    override suspend fun getVideoList(): ReadVideoList {
        val response = apolloClient.query(VideoQuery()).execute()
        var responseList = response.data?.readVideos ?: emptyList()
        val videoList = ReadVideoList(mutableListOf())
        responseList.forEach {
            val video = ReadVideoNode(
                it.id,
                it.name,
                it.fileName,
                it.extension,
                it.uploadDate,
                it.tags
            )
            videoList.list.add(video)
        }
        return videoList
    }

    override suspend fun getVideo(id: Int): ReadVideoNode {
        val response = apolloClient.query(VideoDetailsQuery(id)).execute()
        var responseData = response.data?.readVideos ?: emptyList()
        val videoList = ReadVideoList(mutableListOf())
        responseData.forEach {
            val video = ReadVideoNode(
                it.id,
                it.name,
                it.fileName,
                it.extension,
                it.uploadDate,
                it.tags
            )
            videoList.list.add(video)
        }
        return videoList.list.first()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object MusicManagerModule {
    @Provides
    @Singleton
    fun provideGraphQLManager(apolloClient: ApolloClient): GraphQLManager {
        return GraphQLManagerImpl(apolloClient)
    }
}