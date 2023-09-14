package com.example.collectit.screens.resources.images.item

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.dtos.resources.images.item.ReadImageNode
import com.example.core.managers.CommonStatManager
import com.example.core.managers.GraphQLManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
@DelicateCoroutinesApi
class ImageViewModel @Inject constructor(
    private val graphQLManager: GraphQLManager,
    private val statisticsManager: CommonStatManager
) : ViewModel() {
    private val _image: MutableLiveData<ReadImageNode> = MutableLiveData()
    val image: LiveData<ReadImageNode> get() = _image

    fun getImage(id: Int) {
        viewModelScope.launch {
            val result = graphQLManager.getImage(id)

            _image.value = result
            statisticsManager.postVisitResource(id.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun parseDate() : String{
        var dateStr = image.value!!.uploadDate.toString()
        dateStr = dateStr.subSequence(0, dateStr.length - 5).toString()
        return LocalDateTime.parse(dateStr).format(DateTimeFormatter.ofPattern("dd/M/yyyy hh:mm:ss"))
    }

    fun parseName() : String{
        var temp = image.value!!.fileName.replace('-', '_')
        return temp.subSequence(0, image.value!!.fileName.length - 4).toString().lowercase()
    }
}