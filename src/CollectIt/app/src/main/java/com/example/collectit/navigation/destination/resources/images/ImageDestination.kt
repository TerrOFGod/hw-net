package com.example.collectit.navigation.destination.resources.images

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.collectit.navigation.NavRoute
import com.example.collectit.screens.resources.images.item.ImageCard

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
fun NavGraphBuilder.image(
    navController: NavHostController
){
    composable(
        route = "${NavRoute.Image.path}{id}",
        arguments = listOf(navArgument("id") { type = NavType.IntType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val id = arguments.getInt("id")
        if(id != 0)
            ImageCard(id)
    }
}

fun NavController.navigateToImage(
    imageId: Int
){
    navigate("${NavRoute.Image.path}$imageId")
}