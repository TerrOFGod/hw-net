package com.example.collectit.navigation.destination.statistics

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.collectit.navigation.NavRoute
import com.example.collectit.screens.statistics.StatPage

@ExperimentalMaterial3Api
fun NavGraphBuilder.stat() {
    composable(NavRoute.Stat.path){
        StatPage()
    }
}