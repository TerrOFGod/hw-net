package com.example.collectit.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.collectit.AppModule
import com.example.collectit.infrastructure.AppState
import com.example.collectit.navigation.CollectItNavHost
import com.example.collectit.navigation.NavRoute
import com.example.collectit.ui.components.navigation.NavButtonWithIconComponent.Companion.NavButtonWithIcon
import com.example.collectit.ui.components.navigation.NavButtonWithoutIconComponent.Companion.NavButtonWithoutIcon

class LayoutComponent {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        @ExperimentalMaterial3Api
        @Composable
        fun Layout(
            navController: NavHostController,
            routes: Array<NavRoute>
        ) {
            Scaffold(
                bottomBar = {
                    BottomAppBar(
                        contentPadding = PaddingValues(20.dp, 0.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                                .wrapContentHeight()
                        ) {
                            Row(

                            ){
                                routes.forEach {
                                    if (it.icon != null){
                                        NavButtonWithIcon(navController, it)
                                    }else{
                                        NavButtonWithoutIcon(navController, it)
                                    }
                                }
                            }
                        }


                    }
                }
            ) {innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)){
                    CollectItNavHost(navController)
                }
            }
        }
    }
}