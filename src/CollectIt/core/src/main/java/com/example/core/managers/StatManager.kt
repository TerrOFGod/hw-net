package com.example.core.managers

import kotlinx.coroutines.flow.Flow

interface StatManager {
    suspend fun getMusicStatistics(): Flow<Map<String, Int>>
}