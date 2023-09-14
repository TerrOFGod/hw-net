package com.example.data.managers

import com.example.core.managers.CommonStatManager
import com.example.data.api.StatApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

class CommonStatManagerImpl @Inject constructor(val api: StatApi): CommonStatManager {
    override suspend fun getStatistics(): Map<String, Int> {
        val response = api.getStatistics()
        return response.body()!!
    }

    override suspend fun postVisitResource(id: String) {
        api.postVisitResource(id)
    }

}

@Module
@InstallIn(SingletonComponent::class)
object CommonStatisticsManagerModule {
    @Provides
    @Singleton
    fun provideStatisticsManager(api: StatApi): CommonStatManager {
        return CommonStatManagerImpl(api)
    }
}