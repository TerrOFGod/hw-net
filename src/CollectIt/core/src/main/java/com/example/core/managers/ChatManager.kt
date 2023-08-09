package com.example.core.managers

import com.example.core.dtos.auth.Message
import kotlinx.coroutines.flow.Flow

interface ChatManager {
    suspend fun sendMessage(message: String)
    suspend fun getChatMessages(): Flow<Message>
}