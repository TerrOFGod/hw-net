package com.example.collectit.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

class CustomTagComponent {
    companion object {
        @ExperimentalMaterial3Api
        @Composable
        fun CustomTag(
            onClick: () -> Unit,
            text: String
        ) {
            AssistChip(
                onClick = onClick,
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                label = {
                    Text(
                        text = text,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            )
        }
    }
}
