package com.example.collectit

import android.content.SharedPreferences
import com.example.core.Constants
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.collectit.infrastructure.AppState
import com.example.collectit.navigation.CollectItNavHost
import com.example.collectit.navigation.NavRoute
import com.example.collectit.ui.components.LayoutComponent.Companion.Layout
import com.example.collectit.ui.components.navigation.NavButtonWithIconComponent
import com.example.collectit.ui.components.navigation.NavButtonWithIconComponent.Companion.NavButtonWithIcon
import com.example.collectit.ui.components.navigation.NavButtonWithoutIconComponent
import com.example.collectit.ui.components.navigation.NavButtonWithoutIconComponent.Companion.NavButtonWithoutIcon
import com.example.collectit.ui.theme.CollectItTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.O)
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
                val machine = AppModule.appStateMachine;
                val at = sharedPreferences.getString(
                    Constants.SharedPreferences.ACCESS_TOKEN,
                    ""
                )
                val isAuthenticated = remember {
                    mutableStateOf(
                        at!!.isNotEmpty())
                }
                Scaffold(
                    bottomBar = {
                        BottomAppBar(
                            contentPadding = PaddingValues(20.dp, 0.dp)
                        ) {
                            key(isAuthenticated) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                ) {
                                    Row(

                                    ) {
                                        routes.forEach {
                                            if (it.icon != null) {
                                                NavButtonWithIcon(navController, it)
                                            } else {

                                                if (isAuthenticated.value) {
                                                    AssistChip(
                                                        onClick = {
                                                            isAuthenticated.value = false
                                                            sharedPreferences.edit()
                                                                .remove(Constants.SharedPreferences.ACCESS_TOKEN)
                                                                .apply()
                                                            navController.navigate(NavRoute.Home.path)
                                                        },
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .padding(horizontal = 8.dp),
                                                        label = {
                                                            Text(
                                                                text = NavRoute.LogOut.title,
                                                                fontSize = 25.sp,
                                                            )
                                                        }
                                                    )
                                                } else {
                                                    NavButtonWithoutIcon(navController, it)
                                                }
                                            }
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
                    machine.currentState.observe(this) { newState ->
                        when (newState) {
                            AppState.Home -> navController.navigate(NavRoute.Home.path)
                            AppState.Images -> navController.navigate(NavRoute.Images.path)
                            AppState.Music -> navController.navigate(NavRoute.Music.path)
                            AppState.Video -> navController.navigate(NavRoute.Video.path)
                            AppState.Login -> navController.navigate(NavRoute.Login.path)
                            AppState.SignUp -> navController.navigate(NavRoute.SignUp.path)
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
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


