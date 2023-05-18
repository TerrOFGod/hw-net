package com.example.collectit.navigation.destination.account

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import coil.compose.AsyncImagePainter
import coil.request.SuccessResult
import com.example.collectit.navigation.NavRoute
import com.example.collectit.screens.account.LoginScreen

@ExperimentalMaterial3Api
fun NavGraphBuilder.login(
    navController: NavHostController,
    onSuccessResult: () -> Unit
){
    composable(NavRoute.Login.path){
        LoginScreen(navController = navController, onSuccessResult)
    }
}