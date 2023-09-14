package com.example.data.api

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideUsersApi() : UsersApi {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("http://10.0.2.2:7211/account/")
            .build()
            .create(UsersApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStatisticsApi(): StatApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://10.0.2.2:7222")
        .build()
        .create(StatApi::class.java)
}