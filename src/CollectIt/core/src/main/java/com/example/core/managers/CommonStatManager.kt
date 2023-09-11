package com.example.core.managers

interface CommonStatManager {
    suspend fun getStatistics(): Map<String, Int>

    suspend fun postVisitImage(id: String)

    suspend fun postVisitMusic(id: String)

    suspend fun postVisitVideo(id: String)
}