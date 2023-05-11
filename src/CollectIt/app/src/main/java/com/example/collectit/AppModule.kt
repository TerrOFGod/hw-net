package com.example.collectit

import android.content.Context
import android.content.SharedPreferences
import com.example.collectit.infrastructure.AppStateMachine
import com.example.core.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
}