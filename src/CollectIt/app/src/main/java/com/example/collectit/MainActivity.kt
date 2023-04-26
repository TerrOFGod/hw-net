package com.example.collectit

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.navigation.compose.rememberNavController
import com.example.collectit.navigation.NavRoute
import com.example.collectit.ui.theme.CollectItTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.collectit.ui.components.LayoutComponent.Companion.Layout

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val routes = arrayOf(
            NavRoute.Home,
            NavRoute.Images,
            NavRoute.Music,
            NavRoute.Video,
            NavRoute.Login,
            NavRoute.SignUp
        )
        setContent {
            CollectItTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                Layout(navController = navController, routes = routes)
            }
        }
    }
}


