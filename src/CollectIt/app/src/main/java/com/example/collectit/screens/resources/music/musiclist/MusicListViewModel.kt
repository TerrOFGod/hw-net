package com.example.collectit.screens.resources.music.musiclist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val musicList = MutableLiveData(ArrayList<ReadMusicNode>())

    fun getMusics() {
        viewModelScope.launch(Dispatchers.Main) {
            val musics = runBlocking (Dispatchers.IO){
                graphQLManager.getMusicList().list
            }

            musicList.value = ArrayList(musics)
        }
    }
}