package com.example.collectit

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.collectit.navigation.NavRoute
import com.example.collectit.ui.components.LayoutComponent.Companion.Layout
import com.example.collectit.ui.theme.CollectItTheme
import dagger.hilt.android.AndroidEntryPoint

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
            NavRoute.Login
        )
        setContent {
            CollectItTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                Layout(navController = navController, routes = routes)
            }
        }
    }

    @ExperimentalMaterial3Api
    @Preview(showBackground = true)
    @Composable
    fun PrevLayout(){
        CollectItTheme {
            val routes = arrayOf(
                NavRoute.Home,
                NavRoute.Images,
                NavRoute.Music,
                NavRoute.Video,
                NavRoute.Login
            )
            Layout(navController = rememberNavController(), routes)
        }
    }
}


