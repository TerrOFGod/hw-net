package com.example.collectit.screens.resources.images.list

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.collectit.navigation.NavRoute
import com.example.collectit.navigation.destination.resources.images.navigateToImage
import com.example.collectit.ui.components.basics.BasicImageComponent.Companion.BasicImage
import com.example.collectit.ui.theme.CollectItTheme
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Composable
fun ImagesScreen(
    navController: NavHostController,
    viewModel: ImagesListViewModel = hiltViewModel()
) {
    Log.v("ImagesList", "images list")
    if (!viewModel.imagesList.value!!.any()) {
        viewModel.getImages()
    }
    viewModel.imagesList.value!!.forEach{
        Log.v("ReadImage", it.uploadDate.toString())
    }

    LazyColumn {
        items(viewModel.imagesList.value!!) {
            var dateStr = it.uploadDate.toString()
            dateStr = dateStr.subSequence(0, dateStr.length - 5).toString()
            val date = LocalDateTime.parse(dateStr).format(DateTimeFormatter.ofPattern("dd/M/yyyy hh:mm:ss"))
            BasicImage(
                onClick = {navController.navigateToImage(it.id)},
                url = "${it.fileName}",
                title = it.name,
                date = date,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun PrevImagesScreen(){
    CollectItTheme {
        ImagesScreen(navController = rememberNavController())
    }
}