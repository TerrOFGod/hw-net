package com.example.core.managers

interface CommonStatManager {
    suspend fun getStatistics(): Map<String, Int>

    suspend fun postVisitResource(id: String)
}