package com.example.core.dtos.resources.music.list

import com.example.core.dtos.resources.music.item.ReadMusicNode

data class ReadMusicList(
    val list: MutableList<ReadMusicNode>
)
