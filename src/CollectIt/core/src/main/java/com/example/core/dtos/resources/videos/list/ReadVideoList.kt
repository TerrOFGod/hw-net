package com.example.core.dtos.resources.videos.list

import com.example.core.dtos.resources.videos.item.ReadVideoNode

data class ReadVideoList(
    val list: MutableList<ReadVideoNode>
)
