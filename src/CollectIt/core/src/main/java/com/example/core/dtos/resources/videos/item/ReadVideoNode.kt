package com.example.core.dtos.resources.videos.item

data class ReadVideoNode(
    val name: String,
    val fileName: String,
    val extension: String,
    val uploadDate: Any,
    val tags: List<String>,
)
