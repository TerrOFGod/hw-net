package com.example.collectit.screens.resources.music

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.collectit.ui.theme.CollectItTheme

@Composable
fun MusicListScreen(navController: NavHostController) {
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun prevMusicListScreen(){
    CollectItTheme {
        MusicListScreen(navController = rememberNavController())
    }
}