package com.example.collectit.navigation.destination.resources.images

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.collectit.navigation.NavRoute
import com.example.collectit.screens.resources.images.item.ImageCard

@ExperimentalMaterial3Api
fun NavGraphBuilder.image(
    navController: NavHostController
){
    composable(
        route = "${NavRoute.Image.path}{${NavRoute.Image.id}}",
        arguments = listOf(navArgument(NavRoute.Image.id.toString()) { type = NavType.IntType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val gameCardId = arguments.getInt(NavRoute.Image.id.toString(), 0)
        if(gameCardId != 0)
            ImageCard(
                title = "Bacon ipsum",
                description = "Bacon ipsum Bacon ipsu mBacon ipsBacon ipsum Bacon ipsum umB acon ipsumB acon ipsum"
            )
    }
}