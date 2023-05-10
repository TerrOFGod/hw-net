package com.example.data.api

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApolloModule {
    @Provides
    @Singleton
    fun provideGraphQLApi() : ApolloClient = ApolloClient.Builder()
        .serverUrl("http://10.0.2.2:5211/graphql")
        .build()
}