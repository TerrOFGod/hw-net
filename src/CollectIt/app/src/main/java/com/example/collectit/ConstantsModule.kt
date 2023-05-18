package com.example.collectit

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
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
object ConstantsModule {
    @Singleton
    @Provides
    fun provideAuthenticationState(@ApplicationContext context: Context): MutableLiveData<Boolean> {
        return isAuthorized
    }

    val isAuthorized = MutableLiveData(false)
}