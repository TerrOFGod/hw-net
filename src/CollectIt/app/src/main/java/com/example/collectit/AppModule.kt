package com.example.collectit

import android.content.Context
import android.content.SharedPreferences
import com.example.collectit.infrastructure.AppStateMachine
import com.example.core.Constants
import com.example.core.managers.ChatManager
import com.example.data.api.UsersApi
import com.example.data.managers.GrpcChatManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.grpc.Channel
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideAppStateMachine(): AppStateMachine {
        return appStateMachine
    }

    val appStateMachine = AppStateMachine()

    @Singleton
    @Provides
    fun provideGrpcChannel(): ManagedChannel {
        return ManagedChannelBuilder
            .forAddress("10.0.2.2", 5001
            )
            .usePlaintext()
            .build()
    }

    @Singleton
    @Provides
    fun provideChatManager(sp: SharedPreferences, channel: ManagedChannel, usersApi: UsersApi): ChatManager {
        val token = sp.getString(Constants.SharedPreferences.ACCESS_TOKEN, null) ?: throw Exception(
            "Нет токена в памяти!"
        )
        return GrpcChatManager(channel, token, usersApi)
    }
}