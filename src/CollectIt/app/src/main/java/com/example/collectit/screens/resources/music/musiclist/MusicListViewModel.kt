package com.example.collectit.screens.resources.music.musiclist

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.dtos.resources.images.item.ReadImageNode
import com.example.core.dtos.resources.music.item.ReadMusicNode
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
class MusicListViewModel @Inject constructor(
    private val graphQLManager: GraphQLManager
) : ViewModel() {
    private val _musicList = MutableLiveData(ArrayList<ReadMusicNode>())
    val musicList: LiveData<ArrayList<ReadMusicNode>> get() = _musicList
    val statistics = MutableLiveData<Map<Int, Int>>(HashMap())

    private val statisticsManager: StatManager by lazy {
        RabbitmqStatisticsManagerProvider.provideStatisticsManager()
    }

    fun getMusics() {
        viewModelScope.launch {
            val musics = graphQLManager.getMusicList().list

            _musicList.value = ArrayList(musics)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun parseDate(music: ReadMusicNode) : String{
        var dateStr = music.uploadDate.toString()
        dateStr = dateStr.subSequence(0, dateStr.length - 5).toString()
        return LocalDateTime.parse(dateStr).format(DateTimeFormatter.ofPattern("dd/M/yyyy hh:mm:ss"))
    }

    suspend fun subscribeToStatistics() {
        viewModelScope.launch {
            val f = statisticsManager.getResourceStatistics()
            f.collect {
                Log.i("ViewModel", "Получил новый словарь")
                //it.forEach {
                //    Log.d("Elem", "${it.key}: ${it.value}")
                //}
                statistics.value = it
            }
        }
    }
}