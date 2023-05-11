package com.example.core.managers

import com.example.core.dtos.resources.images.item.ReadImageNode
import com.example.core.dtos.resources.images.list.ReadImagesList
import com.example.core.dtos.resources.music.item.ReadMusicNode
import com.example.core.dtos.resources.music.list.ReadMusicList
import com.example.core.dtos.resources.videos.item.ReadVideoNode
import com.example.core.dtos.resources.videos.list.ReadVideoList

interface GraphQLManager {
    suspend fun getImagesList() : ReadImagesList
    suspend fun getImage(id: Int): ReadImageNode

    suspend fun getMusicList() : ReadMusicList
    suspend fun getMusic(id: Int): ReadMusicNode

    suspend fun getVideoList() : ReadVideoList
    suspend fun getVideo(id: Int): ReadVideoNode
}