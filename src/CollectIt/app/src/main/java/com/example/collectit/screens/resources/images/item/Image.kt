package com.example.collectit.screens.resources.images.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.collectit.ui.components.CustomTagComponent.Companion.CustomTag
import com.example.collectit.ui.theme.CollectItTheme
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalMaterial3Api
@Composable
fun ImageCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,

        ),
        shape = MaterialTheme.shapes.large
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = "//images.ctfassets.net/yadj1kx9rmg0/wtrHxeu3zEoEce2MokCSi/cf6f68efdcf625fdc060607df0f3baef/quwowooybuqbl6ntboz3.jpg"
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
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row (
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 25.dp, vertical = 0.dp)
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
                        text = SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date())
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
                for (i in 1..3)
                    CustomTag(onClick = {}, text = "tag ${i}")
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
                    Text(text = "Buy")
                }
            )
        }
    }
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun prevImageCard(){
    CollectItTheme {
        ImageCard(
            title = "Bacon ipsum",
            description = "Bacon ipsum Bacon ipsu mBacon ipsBacon ipsum Bacon ipsum umB acon ipsumB acon ipsum"
        )
    }
}