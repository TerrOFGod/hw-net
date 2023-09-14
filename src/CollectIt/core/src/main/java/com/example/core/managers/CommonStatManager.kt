package com.example.core.managers

interface CommonStatManager {
    suspend fun getStatistics(): Map<Int, Int>

    suspend fun postVisitResource(id: Int)
}