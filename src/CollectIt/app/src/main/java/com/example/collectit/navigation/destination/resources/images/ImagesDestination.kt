package com.example.collectit.navigation.destination.resources.images

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.collectit.navigation.NavRoute
import com.example.collectit.screens.resources.images.list.ImagesScreen

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
fun NavGraphBuilder.images(
    navController: NavHostController
){
    composable(NavRoute.Images.path){
        ImagesScreen(navController = navController)
    }
}