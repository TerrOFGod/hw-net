package com.example.collectit.ui.components.basics

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.core.dtos.resources.music.item.ReadMusicNode
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode

class BasicMusicComponent {
    companion object {
        @ExperimentalMaterial3Api
        @Composable
        fun BasicMusic(
            onClick: () -> Unit,
            modifier: Modifier,
            music: ReadMusicNode,
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
                        text = "${music.name} - $date"
                    )
                }
            }
        }
    }
}