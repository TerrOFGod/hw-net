package com.example.collectit.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.collectit.navigation.destination.account.*
import com.example.collectit.navigation.destination.home
import com.example.collectit.navigation.destination.resources.images.image
import com.example.collectit.navigation.destination.resources.images.images
import com.example.collectit.navigation.destination.resources.musics.musics
import com.example.collectit.navigation.destination.resources.musics.music
import com.example.collectit.navigation.destination.resources.video.video
import com.example.collectit.navigation.destination.statistics.stat

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Composable
fun CollectItNavHost(
    navController: NavHostController,
    onSuccessResult: () -> Unit
){
    NavHost(navController = navController, startDestination = NavRoute.Home.path){
        home(navController)

        images(navController)
        image(navController)

        musics(navController)
        music(navController)

        video(navController)

        login(navController, onSuccessResult)

        chat(navController)
        signup(navController)

        stat()
    }
}

