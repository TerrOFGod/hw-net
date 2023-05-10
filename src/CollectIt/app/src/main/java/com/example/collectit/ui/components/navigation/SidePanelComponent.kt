package com.example.collectit.ui.components.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.collectit.navigation.NavRoute
import com.example.collectit.ui.theme.CollectItTheme


class SidePanelComponent {
    companion object {
        private val items = listOf(
            NavRoute.Home,
            NavRoute.Profile,
            NavRoute.Images,
            NavRoute.Music,
            NavRoute.Video,
            NavRoute.Login
        )

        @ExperimentalMaterial3Api
        @Composable
        fun SidePanel(
            navController: NavHostController
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.outline
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    items.forEach { NavRoute ->
                        if (NavRoute.icon != null){
                            NavButtonWithIconComponent.NavButtonWithIcon(navController, NavRoute)
                        }else{
                            NavButtonWithoutIconComponent.NavButtonWithoutIcon(navController, NavRoute)
                        }
                    }
                }
            }
        }

        @ExperimentalMaterial3Api
        @Preview(showBackground = true)
        @Composable
        fun prevMassageBox(){
            CollectItTheme {
                SidePanel(navController = rememberNavController())
            }
        }
    }
}