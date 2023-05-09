package com.example.collectit.screens.resources.images.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.dtos.resources.images.item.ReadImageNode
import com.example.core.managers.GraphQLManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ImagesListViewModel @Inject constructor(
    private val graphQLManager: GraphQLManager
) : ViewModel() {
    val imagesList = MutableLiveData(ArrayList<ReadImageNode>())

    fun getImages() {
        viewModelScope.launch(Dispatchers.Main) {
            val images = runBlocking (Dispatchers.IO){
                graphQLManager.getImagesList().list
            }

            imagesList.value = ArrayList(images)
        }
    }
}