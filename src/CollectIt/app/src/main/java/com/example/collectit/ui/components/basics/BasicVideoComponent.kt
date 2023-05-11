package com.example.collectit.ui.components.basics

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.collectit.navigation.NavRoute
import com.example.collectit.ui.theme.CollectItTheme
import com.example.core.dtos.resources.videos.item.ReadVideoNode
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import java.text.SimpleDateFormat
import java.util.*

class BasicVideoComponent {
    companion object {
        @ExperimentalMaterial3Api
        @Composable
        fun BasicVideo(
            onClick: () -> Unit,
            modifier: Modifier,
            video: ReadVideoNode,
            date: String
        ){
            Card(
                modifier = modifier,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,

                    ),
                onClick = onClick,
                shape = MaterialTheme.shapes.large
            ){
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    mainAxisSpacing = 90.dp,
                    mainAxisSize = SizeMode.Wrap
                ){
                    Text(
                        text = "${video.name} - $date"
                    )
                }
            }
        }

        @ExperimentalMaterial3Api
        @Preview(showBackground = true)
        @Composable
        fun prevBasicVideo(){
            CollectItTheme {
                val navController = rememberNavController()
                BasicVideo(
                    onClick = { navController.navigate("${NavRoute.Image.path}1")},
                    date = SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date()),
                    video = ReadVideoNode(
                        1,
                        "Bacon ipsum",
                        "",
                        "",
                        "",
                        emptyList()
                    ),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}