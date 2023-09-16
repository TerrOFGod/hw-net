package com.example.collectit.navigation.destination.resources.video

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.collectit.navigation.NavRoute
import com.example.collectit.screens.resources.images.item.ImageCard
import com.example.collectit.screens.resources.video.video.VideoCard

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
fun NavGraphBuilder.video(
    navController: NavHostController
){
    composable(
        route = "${NavRoute.Video.path}{id}",
        arguments = listOf(navArgument("id") { type = NavType.IntType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val id = arguments.getInt("id")
        if(id != 0)
            VideoCard(id)
    }
}

fun NavController.navigateToVideo(
    imageId: Int
){
    navigate("${NavRoute.Images.path}$imageId")
}