package com.example.collectit.screens.resources.images.list

import androidx.lifecycle.LiveData
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
    private val _imagesList = MutableLiveData(ArrayList<ReadImageNode>())
    val imagesList: LiveData<ArrayList<ReadImageNode>> get() = _imagesList

    fun getImages() {
        viewModelScope.launch{
            val images = graphQLManager.getImagesList().list

            _imagesList.value = ArrayList(images)
        }
    }
}