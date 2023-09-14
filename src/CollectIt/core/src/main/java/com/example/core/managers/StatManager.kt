package com.example.core.managers

import kotlinx.coroutines.flow.Flow

interface StatManager {
    suspend fun getResourceStatistics(): Flow<Map<Int, Int>>
}