package com.example.data.managers

import com.example.core.dtos.auth.Message
import com.example.core.managers.ChatManager
import io.grpc.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GrpcChatManager @Inject() constructor(channel: Channel, private val token: String): ChatManager {
    //private val chatService: ChatServiceGrpc.ChatServiceBlockingStub
    override suspend fun sendMessage(message: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getChatMessages(): Flow<Message> {
        TODO("Not yet implemented")
    }


}