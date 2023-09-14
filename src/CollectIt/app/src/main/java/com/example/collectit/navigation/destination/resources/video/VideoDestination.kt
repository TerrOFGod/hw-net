package com.example.collectit.navigation.destination.resources.video

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.collectit.navigation.NavRoute
import com.example.collectit.screens.resources.video.VideoListScreen

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
fun NavGraphBuilder.video(
    navController: NavHostController
){
    composable(NavRoute.Video.path){
        VideoListScreen(navController = navController)
    }
}