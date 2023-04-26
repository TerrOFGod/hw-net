package com.example.collectit.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import com.example.collectit.navigation.CollectItNavHost
import com.example.collectit.navigation.NavRoute

class LayoutComponent {
    companion object {
        @ExperimentalMaterial3Api
        @Composable
        fun Layout(
            navController: NavHostController,
            routes: Array<NavRoute>
        ) {
            Scaffold(bottomBar = {
                BottomAppBar {
                    routes.forEach {
                        Button(onClick = { navController.navigate(it.path) }) {
                            Text(text = it.title)
                        }
                    }
                }
            }) {padd ->
                Box(modifier = Modifier.padding(padd)){
                    val i = padd
                    CollectItNavHost(navController)
                }
            }
        }
    }
}