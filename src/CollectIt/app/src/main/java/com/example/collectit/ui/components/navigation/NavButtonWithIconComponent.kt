package com.example.collectit.ui.components.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.collectit.navigation.NavRoute

class NavButtonWithIconComponent {
    companion object {
        @ExperimentalMaterial3Api
        @Composable
        fun NavButtonWithIcon(
            navController: NavHostController,
            NavRoute: NavRoute
        ){
            IconButton(
                onClick = { navController.navigate(NavRoute.path) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }},


            ) {
                Icon(
                    painter = painterResource(id = NavRoute.icon!!),
                    contentDescription = null,
                    modifier = Modifier.size(35.dp,35.dp)
                )
            }

        }
    }
}