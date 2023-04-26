package com.example.collectit.ui.components.basics

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.collectit.navigation.NavRoute
import com.example.collectit.ui.theme.CollectItTheme
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
            url: String,
            title: String,
            date: String
        ){
            Card(
                modifier = modifier,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,

                    ),
                shape = MaterialTheme.shapes.large
            ){
                Image(
                    painter = rememberAsyncImagePainter(
                        model = url
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.large)
                        .fillMaxWidth()
                        .aspectRatio(3f / 2f)
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    mainAxisSpacing = 90.dp,
                    mainAxisSize = SizeMode.Wrap
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = date,
                        style = MaterialTheme.typography.bodyMedium
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
                    onClick = { navController.navigate("${NavRoute.Image.path}/{${NavRoute.Image.id}}")},
                    url = "//images.ctfassets.net/yadj1kx9rmg0/wtrHxeu3zEoEce2MokCSi/cf6f68efdcf625fdc060607df0f3baef/quwowooybuqbl6ntboz3.jpg",
                    title = "Bacon ipsum",
                    date = SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date()),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}