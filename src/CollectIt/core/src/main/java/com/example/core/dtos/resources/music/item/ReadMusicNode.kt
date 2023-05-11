package com.example.core.dtos.resources.music.item

data class ReadMusicNode(
    val id: Int,
    val name: String,
    val fileName: String,
    val extension: String,
    val uploadDate: Any,
    val tags: List<String>,
)
