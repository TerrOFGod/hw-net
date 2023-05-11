package com.example.collectit.screens.resources.images.item

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.collectit.ui.components.CustomTagComponent.Companion.CustomTag
import com.example.collectit.ui.theme.CollectItTheme
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Composable
fun ImageCard(
    id: Int,
    modifier: Modifier = Modifier,
    viewModel: ImageViewModel = hiltViewModel()
) {
    Log.v("Image_$id", "get image")
    viewModel.getImage(id)

    val a = 0;
    Log.v("ReadImage", viewModel.image.value!!.uploadDate.toString())

    var dateStr = viewModel.image.value!!.uploadDate.toString()
    dateStr = dateStr.subSequence(0, dateStr.length - 5).toString()
    val date = LocalDateTime.parse(dateStr).format(DateTimeFormatter.ofPattern("dd/M/yyyy hh:mm:ss"))

    var temp = viewModel.image.value!!.fileName.replace('-', '_')
    val name = temp.subSequence(0, viewModel.image.value!!.fileName.length - 4).toString().lowercase()
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
        shape = MaterialTheme.shapes.large
    ) {
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
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = viewModel.image.value!!.name,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row (
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(horizontal = 25.dp, vertical = 0.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(5.dp)
                    ),

            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(5.dp),
                ) {
                    Text(
                        text = "Дата загрузки",
                        style = TextStyle(
                            fontWeight = FontWeight.W800
                        )
                    )
                    Text(
                        text = date
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(5.dp),
                ) {
                    Text(
                        text = "Автор",
                        style = TextStyle(
                            fontWeight = FontWeight.W800
                        )
                    )
                    Text(
                        text = "BestPhotoshoper"
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                mainAxisSpacing = 8.dp,
                mainAxisSize = SizeMode.Wrap
            ) {
                viewModel.image.value!!.tags.forEach {
                    CustomTag(onClick = {}, text = it)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            AssistChip(
                onClick = { },
                colors = AssistChipDefaults.assistChipColors(
                    leadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingCart,
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = "Приобрести")
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun prevImageCard(){
    CollectItTheme {
        ImageCard(1)
    }
}