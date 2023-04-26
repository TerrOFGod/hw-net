package com.example.collectit.navigation.destination

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.collectit.navigation.NavRoute
import com.example.collectit.screens.HomeScreen

@ExperimentalMaterial3Api
fun NavGraphBuilder.home(
    navController: NavHostController
){
    composable(NavRoute.Home.path){
        HomeScreen(navController = navController)
    }
}