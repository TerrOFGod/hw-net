package com.example.collectit.screens.resources.music.music

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.dtos.resources.images.item.ReadImageNode
import com.example.core.dtos.resources.music.item.ReadMusicNode
import com.example.core.managers.GraphQLManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
@DelicateCoroutinesApi
class MusicViewModel @Inject constructor(
    private val graphQLManager: GraphQLManager
) : ViewModel() {
    private val _music: MutableLiveData<ReadMusicNode> = MutableLiveData()
    val music: LiveData<ReadMusicNode> get() = _music

    fun getMusic(id: Int) {
        viewModelScope.launch {
            val result = graphQLManager.getMusic(id)

            _music.value = result
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun parseDate() : String{
        var dateStr = music.value!!.uploadDate.toString()
        dateStr = dateStr.subSequence(0, dateStr.length - 5).toString()
        return LocalDateTime.parse(dateStr).format(DateTimeFormatter.ofPattern("dd/M/yyyy hh:mm:ss"))
    }

    fun parseName() : String{
        var temp = music.value!!.fileName.replace('-', '_')
        return temp.subSequence(0, music.value!!.fileName.length - 4).toString().lowercase()
    }
}