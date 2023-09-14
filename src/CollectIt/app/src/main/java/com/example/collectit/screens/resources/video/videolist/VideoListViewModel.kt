package com.example.collectit.screens.resources.video.videolist

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.dtos.resources.music.item.ReadMusicNode
import com.example.core.dtos.resources.videos.item.ReadVideoNode
import com.example.core.managers.GraphQLManager
import com.example.core.managers.StatManager
import com.example.data.managers.RabbitmqStatisticsManagerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val graphQLManager: GraphQLManager
) : ViewModel() {
    private val _videoList = MutableLiveData(ArrayList<ReadVideoNode>())
    val videoList: LiveData<ArrayList<ReadVideoNode>> get() = _videoList
    val statistics = MutableLiveData<Map<String, Int>>(HashMap())

    private val statisticsManager: StatManager by lazy {
        RabbitmqStatisticsManagerProvider.provideStatisticsManager()
    }

    fun getVideos() {
        viewModelScope.launch {
            val videos = graphQLManager.getVideoList().list

            _videoList.value = ArrayList(videos)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun parseDate(video: ReadVideoNode) : String{
        var dateStr = video.uploadDate.toString()
        dateStr = dateStr.subSequence(0, dateStr.length - 5).toString()
        return LocalDateTime.parse(dateStr).format(DateTimeFormatter.ofPattern("dd/M/yyyy hh:mm:ss"))
    }

    suspend fun subscribeToStatistics() {
        viewModelScope.launch {
            val f = statisticsManager.getMusicStatistics()
            f.collect {
                Log.i("ViewModel", "Получил новый словарь")
                it.forEach {
                    Log.d("Elem", "${it.key}: ${it.value}")
                }
                statistics.value = it
            }
        }
    }
}