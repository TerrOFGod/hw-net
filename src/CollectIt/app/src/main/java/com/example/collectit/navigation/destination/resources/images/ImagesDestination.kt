package com.example.collectit.navigation.destination.resources.images

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.collectit.navigation.NavRoute
import com.example.collectit.screens.resources.images.ImagesScreen

@ExperimentalMaterial3Api
fun NavGraphBuilder.images(
    navController: NavHostController
){
    composable(NavRoute.Images.path){
        ImagesScreen(navController = navController)
    }
}