package com.example.collectit.navigation.destination.resources.musics

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.collectit.navigation.NavRoute
import com.example.collectit.screens.resources.music.MusicCard

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
fun NavGraphBuilder.music(
    navController: NavHostController
){
    composable(
        route = "${NavRoute.Music.path}{id}",
        arguments = listOf(navArgument("id") { type = NavType.IntType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val id = arguments.getInt("id")
        if(id != 0)
            MusicCard(id)
    }
}

fun NavController.navigateToMusic(
    musicId: Int
){
    navigate("${NavRoute.Music.path}$musicId")
}