package com.example.collectit.screens.account.chat

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.dtos.auth.Message
import com.example.core.managers.ChatManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.grpc.StatusException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatManager: ChatManager
) : ViewModel() {
    private var updateMessagesFlow: Job? = null

    val messages = mutableStateListOf<Message>()
    var inputMessage by mutableStateOf("")

    override fun onCleared() {
        super.onCleared()
        updateMessagesFlow?.cancel()
    }

    fun sendMessage() {
        if (inputMessage.isEmpty())
            return

        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ChatPageViewModel", "Внутри viewModelScope.launch")
            try {
                chatManager.sendMessage(inputMessage)
                inputMessage = ""
            }
            catch (e: StatusException) {
                Log.e("ChatPageViewModel", "Ошибка во время отправки сообщения: ${e.localizedMessage}. ${e.status}. ${e.message}", e)
            }
        }
    }

    fun observeMessages() {
        Log.d("ChatPageViewModel", "observeMessages")
        updateMessagesFlow = viewModelScope.launch(Dispatchers.IO) {
            chatManager
                .getChatMessages()
                .collect {
                    Log.d("ChatPageViewModel", "Получено сообщение $it")

                    messages.add(Message(username = it.username, message = it.message))
                }
        }
    }
}