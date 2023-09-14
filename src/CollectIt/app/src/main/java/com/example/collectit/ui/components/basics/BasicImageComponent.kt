package com.example.collectit.ui.components.basics

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import com.example.collectit.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.collectit.navigation.NavRoute
import com.example.collectit.ui.theme.CollectItTheme
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class BasicImageComponent {
    companion object {
        @ExperimentalMaterial3Api
        @Composable
        fun BasicImage(
            onClick: () -> Unit,
            modifier: Modifier,
            url: String,
            title: String,
            date: String,
            traffic: Int
        ){
            var temp = url.replace('-', '_')
            val name = temp.subSequence(0, url.length - 4).toString().lowercase()
            val context = LocalContext.current
            val drawableId = remember(name) {
                context.resources.getIdentifier(
                    name,
                    "drawable",
                    context.packageName
                )
            }

            Card(
                modifier = modifier,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,

                    ),
                onClick = onClick,
                shape = MaterialTheme.shapes.large
            ){
                Image(
                    painter = rememberAsyncImagePainter(
                        model = drawableId
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.large)
                        .fillMaxWidth()
                        .aspectRatio(3f / 2f)
                )
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
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
                    Text(
                        text = "Visits - $traffic",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        @ExperimentalMaterial3Api
        @Preview(showBackground = true)
        @Composable
        fun prevBasicImage(){
            CollectItTheme {
                val navController = rememberNavController()
                BasicImage(
                    onClick = { navController.navigate("${NavRoute.Image.path}1")},
                    url = "//images.ctfassets.net/yadj1kx9rmg0/wtrHxeu3zEoEce2MokCSi/cf6f68efdcf625fdc060607df0f3baef/quwowooybuqbl6ntboz3.jpg",
                    title = "Bacon ipsum",
                    date = SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date()),
                    modifier = Modifier.padding(16.dp),
                    traffic = 0
                )
            }
        }
    }
}