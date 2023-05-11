package com.example.collectit.screens.resources.video

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.collectit.navigation.NavRoute
import com.example.collectit.screens.resources.video.videolist.VideoListViewModel
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
    Log.v("MusicList", "music list")
    if (!viewModel.videoList.value!!.any()) {
        viewModel.getMusics()
    }
    viewModel.videoList.value!!.forEach{
        Log.v("ReadMusic", it.uploadDate.toString())
    }
    LazyColumn {
        items(viewModel.videoList.value!!) {
            var dateStr = it.uploadDate.toString()
            dateStr = dateStr.subSequence(0, dateStr.length - 5).toString()
            val date = LocalDateTime.parse(dateStr).format(DateTimeFormatter.ofPattern("dd/M/yyyy hh:mm:ss"))
            BasicVideo(
                onClick = {navController.navigate("${NavRoute.Music.path}1")},
                date = date,
                video = it,
                modifier = Modifier.padding(16.dp)
            )
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