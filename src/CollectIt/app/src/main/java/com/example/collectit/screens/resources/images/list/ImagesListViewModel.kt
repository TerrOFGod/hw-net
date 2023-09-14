package com.example.collectit.screens.resources.images.list

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.dtos.resources.images.item.ReadImageNode
import com.example.core.managers.GraphQLManager
import com.example.core.managers.StatManager
import com.example.data.managers.RabbitmqStatisticsManagerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ImagesListViewModel @Inject constructor(
    private val graphQLManager: GraphQLManager
) : ViewModel() {

    private val _imagesList = MutableLiveData(ArrayList<ReadImageNode>())

    val imagesList: LiveData<ArrayList<ReadImageNode>> get() = _imagesList

    val statistics = MutableLiveData<Map<String, Int>>(HashMap())

    private val statisticsManager: StatManager by lazy {
        RabbitmqStatisticsManagerProvider.provideStatisticsManager()
    }

    fun getImages() {
        viewModelScope.launch{
            val images = graphQLManager.getImagesList().list

            _imagesList.value = ArrayList(images)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun parseDate(image: ReadImageNode) : String{
        var dateStr = image.uploadDate.toString()
        dateStr = dateStr.subSequence(0, dateStr.length - 5).toString()
        return LocalDateTime.parse(dateStr).format(DateTimeFormatter.ofPattern("dd/M/yyyy hh:mm:ss"))
    }

    suspend fun subscribeToStatistics() {
        viewModelScope.launch {
            val f = statisticsManager.getMusicStatistics()
            f.collect {
                Log.i("ViewModel", "Получил новый словарь")
                it.forEach {
                    Log.d("Elem", "${it.key}: ${it.value}")
                }
                statistics.value = it
            }
        }
    }
}