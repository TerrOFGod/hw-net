package com.example.collectit.screens.resources.video

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
import com.example.collectit.navigation.NavRoute
import com.example.collectit.navigation.destination.resources.video.navigateToVideo
import com.example.collectit.screens.resources.video.videolist.VideoListViewModel
import com.example.collectit.ui.components.basics.BasicMusicComponent
import com.example.collectit.ui.components.basics.BasicVideoComponent.Companion.BasicVideo
import com.example.collectit.ui.theme.CollectItTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExperimentalMaterial3Api
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VideoListScreen(
    navController: NavHostController,
    viewModel: VideoListViewModel = hiltViewModel()
) {

    Log.v("VideoList", "Start observe state")
    // State
    val videoList = viewModel.videoList.observeAsState()
    val statistics = viewModel.statistics.observeAsState()

    Log.v("VideoList", "API Call")
    // API call
    LaunchedEffect(key1 = Unit) {
        viewModel.getVideos()
        viewModel.subscribeToStatistics()
    }
    if (videoList.value == null) {
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
                items(videoList.value!!) {
                    val traffic = map.getOrDefault(it.id, 0)
                    Log.d("VideoListPage", "Кол-во просмотров для ${it.id} = ${traffic}")
                    var date = viewModel.parseDate(it)
                    BasicVideo(
                        onClick = {navController.navigateToVideo(it.id)},
                        date = date,
                        video = it,
                        modifier = Modifier.padding(16.dp),
                        traffic = traffic
                    )
                }
            } else {
                Log.e("MusicListPage", "Словарь null")
                items(videoList.value!!) {
                    var date = viewModel.parseDate(it)
                    BasicVideo(
                        onClick = {navController.navigateToVideo(it.id)},
                        date = date,
                        video = it,
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
fun prevVideoListScreen(){
    CollectItTheme {
        VideoListScreen(navController = rememberNavController())
    }
}