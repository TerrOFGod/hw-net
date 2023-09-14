package com.example.collectit.screens.statistics

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.collectit.screens.resources.video.videolist.VideoListViewModel

@Composable
fun StatPage(viewModel: StatViewModel = hiltViewModel()) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        try {
            viewModel.getStatistics()
        } catch (e: Exception) {
            Toast.makeText(context, "Ошибка запроса", Toast.LENGTH_SHORT).show()
        }
    }

    val map by viewModel.statistics.observeAsState()

    Log.i("StatisticsPage", "Создаю страницу Statistics")
    StatisticsPageInner(map = map)
}

@Composable
fun StatisticsPageInner(map: Map<String, Int>?) {
    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 4.dp)
    ) {
        if (map == null) {
            item {
                Text(text = "грузимся, сорян(")
            }
        } else {
            items(map.toList()) {
                Button(onClick = {}, shape = RectangleShape, modifier = Modifier.fillParentMaxWidth()) {
                    Text(text = "${it.first}: ${it.second}")
                }
            }
        }
    }
}