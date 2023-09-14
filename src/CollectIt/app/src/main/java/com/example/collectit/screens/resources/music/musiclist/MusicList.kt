package com.example.collectit.screens.resources.music

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
import com.example.collectit.navigation.destination.resources.musics.navigateToMusic
import com.example.collectit.screens.resources.music.musiclist.MusicListViewModel
import com.example.collectit.ui.components.basics.BasicMusicComponent.Companion.BasicMusic
import com.example.collectit.ui.theme.CollectItTheme

@ExperimentalMaterial3Api
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MusicListScreen(
    navController: NavHostController,
    viewModel: MusicListViewModel = hiltViewModel()
) {
    Log.v("MusicList", "Start observe state")
    // State
    val musicList = viewModel.musicList.observeAsState()
    val statistics = viewModel.statistics.observeAsState()
    
    Log.v("MusicList", "API Call")
    // API call
    LaunchedEffect(key1 = Unit) {
        viewModel.getMusics()
        viewModel.subscribeToStatistics()
    }
    if (musicList.value == null) {
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
                items(musicList.value!!) {
                    val traffic = map.getOrDefault(it.id, 0)
                    Log.d("MusicListPage", "Кол-во просмотров для ${it.id} = ${traffic}")
                    var date = viewModel.parseDate(it)
                    BasicMusic(
                        onClick = {navController.navigateToMusic(it.id)},
                        modifier = Modifier.padding(16.dp),
                        music = it,
                        date = date,
                        traffic = traffic
                    )
                }
            } else {
                Log.e("MusicListPage", "Словарь null")
                items(musicList.value!!) {
                    var date = viewModel.parseDate(it)
                    BasicMusic(
                        onClick = {navController.navigateToMusic(it.id)},
                        modifier = Modifier.padding(16.dp),
                        music = it,
                        date = date,
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
fun prevMusicListScreen(){
    CollectItTheme {
        MusicListScreen(navController = rememberNavController())
    }
}