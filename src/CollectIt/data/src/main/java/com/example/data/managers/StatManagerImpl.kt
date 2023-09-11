package com.example.data.managers

import android.util.Log
import com.example.core.managers.StatManager
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson


//rabbit
import com.rabbitmq.client.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


//coroutines
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class StatManagerImpl(private val connectionFactory: ConnectionFactory,
                      private val exchangeName: String,
                      private val routingKey: String = ""): StatManager {


    override suspend fun getMusicStatistics(): Flow<Map<String, Int>> {
        return suspendCoroutine {
            Log.i(LogTag, "Создаю соединение")
//            connectionFactory.setThreadFactory(ThreadFactory)
            val connection = connectionFactory.newConnection()
            Log.i(LogTag, "Создаю канал")
            val channel = connection.createChannel()
            Log.i(LogTag, "Канал создан. Декларирую обменник")
            channel.exchangeDeclare(exchangeName, "fanout")
            Log.i(LogTag, "Обменник создан. Создаю очередь")
            val queue = channel.queueDeclare()
            Log.i(LogTag, "Очередь создана. Связываю с обменником")
            channel.queueBind(queue.queue, exchangeName, routingKey)
            Log.i(LogTag, "Очередь связана. Подписываюсь на события")
            it.resume(callbackFlow {
                val consumerTag = channel.basicConsume(queue.queue, true, object: DefaultConsumer(channel) {
                    override fun handleDelivery(
                        consumerTag: String?,
                        envelope: Envelope?,
                        properties: AMQP.BasicProperties?,
                        body: ByteArray?
                    ) {
                        if (body != null) {
                            try {
                                trySend(deserializeStatisticsRecord(body))
                            } catch(e: Exception) {
                                Log.e(LogTag, "Ошибка десериализации сообщения из рэббита")
                            }
                        }
                        super.handleDelivery(consumerTag, envelope, properties, body)
                    }
                })

                Log.d(LogTag, "Зарегистрирован обработчик. Присвоен тэг $consumerTag")

                awaitClose {
                    Log.i(LogTag, "Отменяю подписку")
                    channel.basicCancel(consumerTag)
                    Log.i(LogTag, "Подписка отменена")
                }
            })
        }
    }

    companion object {
        val LogTag = StatManagerImpl::class.simpleName
        val gson = Gson()
        private val mapType = object: TypeToken<Map<String, Int>>(){}.type
        fun deserializeStatisticsRecord(payload: ByteArray): Map<String, Int> {
            val str = payload.decodeToString()
            return gson.fromJson(str, mapType)
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RabbitmqStatisticsManagerProvider {
    val connectionFactory = ConnectionFactory().apply {
        setUri("amqp://10.0.2.2:5672")
        virtualHost = "/"
        username = "guest"
        password = "guest"
    }

    @Provides
    fun provideStatisticsManager(): StatManager {
        return StatManagerImpl(connectionFactory, "statistics-exchange", "")
    }
}