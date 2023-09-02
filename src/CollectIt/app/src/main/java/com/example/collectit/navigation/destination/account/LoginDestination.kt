package com.example.collectit.navigation.destination.account

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.collectit.navigation.NavRoute
import com.example.collectit.screens.account.login.LoginScreen

@ExperimentalMaterial3Api
fun NavGraphBuilder.login(
    navController: NavHostController,
    onSuccessResult: () -> Unit
){
    composable(NavRoute.Login.path){
        LoginScreen(navController = navController, onSuccessResult)
    }
}