package com.example.collectit.screens.resources.video.video

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.dtos.resources.music.item.ReadMusicNode
import com.example.core.dtos.resources.videos.item.ReadVideoNode
import com.example.core.managers.CommonStatManager
import com.example.core.managers.GraphQLManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
@DelicateCoroutinesApi
class VideoViewModel @Inject constructor(
    private val graphQLManager: GraphQLManager,
    private val statisticsManager: CommonStatManager
) : ViewModel() {
    private val _video: MutableLiveData<ReadVideoNode> = MutableLiveData()
    val video: LiveData<ReadVideoNode> get() = _video

    fun getVideo(id: Int) {
        viewModelScope.launch {
            val result = graphQLManager.getVideo(id)

            _video.value = result
            statisticsManager.postVisitResource(id.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun parseDate() : String{
        var dateStr = video.value!!.uploadDate.toString()
        dateStr = dateStr.subSequence(0, dateStr.length - 5).toString()
        return LocalDateTime.parse(dateStr).format(DateTimeFormatter.ofPattern("dd/M/yyyy hh:mm:ss"))
    }

    fun parseName() : String{
        var temp = video.value!!.fileName.replace('-', '_')
        return temp.subSequence(0, video.value!!.fileName.length - 4).toString().lowercase()
    }
}