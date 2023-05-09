package com.example.core.managers

import com.example.core.dtos.resources.images.item.ReadImageNode
import com.example.core.dtos.resources.images.list.ReadImagesList

interface GraphQLManager {
    suspend fun getImagesList() : ReadImagesList
    suspend fun getImage(id: String): ReadImageNode
}