package com.example.data.managers

import com.apollographql.apollo3.ApolloClient
import com.example.core.dtos.resources.images.item.ReadImageNode
import com.example.core.dtos.resources.images.list.ReadImagesList
import com.example.core.managers.GraphQLManager
import com.example.data.ImagesQuery
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

    override suspend fun getImage(id: String): ReadImageNode {
        TODO("Not yet implemented")
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