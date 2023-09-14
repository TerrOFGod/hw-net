package com.example.collectit.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.collectit.navigation.NavRoute
import com.example.collectit.ui.components.basics.BasicImageComponent
import com.example.collectit.ui.theme.CollectItTheme
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
//        floatingActionButton = {
//            FloatingActionButton(onClick = {navController.navigate(NavRoute.Add.path)}) {
//                Icon(
//                    imageVector = Icons.Default.Add,
//                    contentDescription = null,
//                    tint = MaterialTheme.colorScheme.onPrimaryContainer
//                )
//            }
//        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LazyColumn {
                items(20) {
                    BasicImageComponent.BasicImage(
                        onClick = {navController.navigate("${NavRoute.Images.path}1")},
                        url = "abstract_img.jpg",
                        title = "Bacon ipsum",
                        date = SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date()),
                        modifier = Modifier.padding(16.dp),
                        traffic = 0
                    )
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun prevHomeScreen(){
    CollectItTheme {
        HomeScreen(navController = rememberNavController())
    }
}