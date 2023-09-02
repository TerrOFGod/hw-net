package com.example.data.managers

import android.util.Log
import com.example.core.dtos.auth.Message
import com.example.core.managers.ChatManager

import com.example.data.ChatServiceGrpc
import com.example.data.SendMessageRequest
import com.google.protobuf.Empty


import io.grpc.CallCredentials
import io.grpc.Channel
import io.grpc.Metadata
import io.grpc.Metadata.BinaryMarshaller
import io.grpc.okhttp.internal.Credentials

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

import java.util.concurrent.Executor
import javax.inject.Inject

class GrpcChatManager @Inject() constructor(channel: Channel, private val token: String): ChatManager {
    private val chatService: ChatServiceGrpc.ChatServiceBlockingStub

    init {
        chatService = ChatServiceGrpc.newBlockingStub(channel)
            .withCallCredentials(object: CallCredentials() {
                override fun applyRequestMetadata(
                    requestInfo: RequestInfo?,
                    appExecutor: Executor?,
                    applier: MetadataApplier?
            ) {
                applier?.apply(Metadata().apply {

                    put(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER), "Bearer $token")
                })
            }

            override fun thisUsesUnstableApi() {
                // Это че такое?
            }
        })
    }

    override suspend fun sendMessage(message: String) {
        val request = SendMessageRequest.newBuilder()
            .setMessage(message)
            .build()
        Log.d("GrpcChatManager", "Запрошена отправка сообщения на сервер")
        chatService.sendMessage(request)
        Log.d("GrpcChatManager", "Сообщение отправлено на сервер")
    }

    override suspend fun getChatMessages(): Flow<Message> {
        Log.d("GrpcService", "Запрошено получение потока сообщений")
        sendMessage("Даров, помоги по-братски")
        val response = chatService.getMessagesStream(Empty.getDefaultInstance())
        return callbackFlow {
            response.forEach {
                trySend(Message(username = it.username, message = it.message))
            }

            awaitClose {

            }
        }
    }


}