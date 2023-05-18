package com.example.collectit.screens.account.chat

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ChatPageItem(username: String, message: String) {
    Row {
        Text(text = "$username: ",  fontWeight = FontWeight.Bold, color = Color.Black)
        Text(text = message, color = Color.Black)
    }
}

@Preview
@Composable
fun ChatPageItemPreview() {
    ChatPageItem(username = "sample", message = "message")
}