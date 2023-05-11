package com.example.core.dtos.resources.images.item

data class ReadImageNode(
    val id: Int,
    val name: String,
    val fileName: String,
    val extension: String,
    val uploadDate: Any,
    val tags: List<String>,
)
