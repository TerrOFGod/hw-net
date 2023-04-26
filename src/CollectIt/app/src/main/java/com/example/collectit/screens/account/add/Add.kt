package com.example.collectit.screens.account

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.collectit.ui.theme.CollectItTheme

@Composable
fun AddScreen(navController: NavHostController) {
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun prevAddScreen(){
    CollectItTheme {
        AddScreen(navController = rememberNavController())
    }
}