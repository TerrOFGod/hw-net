package com.example.collectit.navigation.destination.account

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.collectit.navigation.NavRoute
import com.example.collectit.screens.account.chat.ChatScreen

@ExperimentalMaterial3Api
fun NavGraphBuilder.chat(
    navController: NavHostController
){
    composable(NavRoute.Chat.path){
        ChatScreen(navController = navController)
    }
}