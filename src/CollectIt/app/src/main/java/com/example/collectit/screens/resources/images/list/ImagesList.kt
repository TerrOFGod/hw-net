package com.example.collectit.screens.resources.images.list

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.collectit.navigation.destination.resources.images.navigateToImage
import com.example.collectit.ui.components.basics.BasicImageComponent.Companion.BasicImage
import com.example.collectit.ui.theme.CollectItTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Composable
fun ImagesScreen(
    navController: NavHostController,
    viewModel: ImagesListViewModel = hiltViewModel()
) {
    Log.v("ImagesList", "Start observe state")
    // State
    val images = viewModel.imagesList.observeAsState()
    val statistics = viewModel.statistics.observeAsState()

    Log.v("ImagesList", "API Call")
    // API call
    LaunchedEffect(key1 = Unit) {
        viewModel.getImages()
        viewModel.subscribeToStatistics()
    }

    if (images.value == null) {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
    else{
        LazyColumn {
            val map = statistics.value
            if (map != null) {
                Log.e("ImagesList", "Словарь не null")
                items(images.value!!) {
                    val traffic = map.getOrDefault(it.id, 0)
                    Log.d("MusicListPage", "Кол-во просмотров для ${it.id} = ${traffic}")
                    var date = viewModel.parseDate(it)
                    BasicImage(
                        onClick = {navController.navigateToImage(it.id)},
                        url = "${it.fileName}",
                        title = it.name,
                        date = date,
                        modifier = Modifier.padding(16.dp),
                        traffic = traffic
                    )
                }
            } else {
                Log.e("MusicListPage", "Словарь null")
                items(images.value!!) {
                    var date = viewModel.parseDate(it)
                    BasicImage(
                        onClick = {navController.navigateToImage(it.id)},
                        url = "${it.fileName}",
                        title = it.name,
                        date = date,
                        modifier = Modifier.padding(16.dp),
                        traffic = 0
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun PrevImagesScreen(){
    CollectItTheme {
        ImagesScreen(navController = rememberNavController())
    }
}