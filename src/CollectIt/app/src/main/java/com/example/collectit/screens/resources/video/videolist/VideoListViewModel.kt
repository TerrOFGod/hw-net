package com.example.collectit.screens.resources.video.videolist

import androidx.lifecycle.LiveData
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
    private val _videoList = MutableLiveData(ArrayList<ReadVideoNode>())
    val videoList: LiveData<ArrayList<ReadVideoNode>> get() = _videoList

    fun getVideos() {
        viewModelScope.launch {
            val videos = graphQLManager.getVideoList().list

            _videoList.value = ArrayList(videos)
        }
    }
}