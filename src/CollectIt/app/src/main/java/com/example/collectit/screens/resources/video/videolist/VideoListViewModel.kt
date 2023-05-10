package com.example.collectit.screens.resources.video.videolist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.dtos.resources.music.item.ReadMusicNode
import com.example.core.dtos.resources.videos.item.ReadVideoNode
import com.example.core.managers.GraphQLManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val graphQLManager: GraphQLManager
) : ViewModel() {
    val videoList = MutableLiveData(ArrayList<ReadVideoNode>())

    fun getMusics() {
        viewModelScope.launch(Dispatchers.Main) {
            val videos = runBlocking (Dispatchers.IO){
                graphQLManager.getVideoList().list
            }

            videoList.value = ArrayList(videos)
        }
    }
}