package com.example.collectit.screens.resources.music.musiclist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.dtos.resources.images.item.ReadImageNode
import com.example.core.dtos.resources.music.item.ReadMusicNode
import com.example.core.managers.GraphQLManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MusicListViewModel @Inject constructor(
    private val graphQLManager: GraphQLManager
) : ViewModel() {
    private val _musicList = MutableLiveData(ArrayList<ReadMusicNode>())
    val musicList: LiveData<ArrayList<ReadMusicNode>> get() = _musicList

    fun getMusics() {
        viewModelScope.launch {
            val musics = graphQLManager.getMusicList().list

            _musicList.value = ArrayList(musics)
        }
    }
}