package com.example.collectit.screens.resources.images.item

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
class ImageViewModel @Inject constructor(
    private val graphQLManager: GraphQLManager
) : ViewModel() {
    val image = MutableLiveData<ReadImageNode>()

    fun getImage(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val img = runBlocking (Dispatchers.IO){
                graphQLManager.getImage(id)
            }

            image.value = img
        }
    }
}