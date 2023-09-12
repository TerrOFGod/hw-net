package com.example.collectit.screens.account.chat

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.collectit.R
import com.example.collectit.ui.theme.CollectItTheme

@Composable
fun ChatScreen(
    navController: NavHostController,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    Log.v("Chat", "Start observe state")

    LaunchedEffect(Unit) {
        try {
            viewModel.observeMessages()
        } catch(e: Exception) {
            Toast.makeText(context, "На сервере нет админов", Toast.LENGTH_SHORT).show()
        }
    }

    Column() {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 4.dp),
        ) {
            items(viewModel.messages) {
                Message(username = it.username, message = it.message)
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ){
            Box {
                TextField(
                    label = { Text(text = "Сообщение...") },
                    value = viewModel.inputMessage,
                    onValueChange = { viewModel.inputMessage = it })
            }
            IconButton(onClick = {
                Toast.makeText(context, "Пока нельзя", Toast.LENGTH_LONG).show()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.attach),
                    contentDescription = null,
                    modifier = Modifier.size(35.dp,35.dp)
                )
            }
            IconButton(onClick = {
                try {
                    viewModel.sendMessage()
                } catch (e: Exception) {
                    Toast.makeText(context, "Админ ушел", Toast.LENGTH_SHORT).show()
                }
            },
                enabled = viewModel.inputMessage.isNotEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.send),
                    contentDescription = null,
                    modifier = Modifier.size(35.dp,35.dp)
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun prevAddScreen(){
    CollectItTheme {
        //ChatScreen(navController = rememberNavController())
    }
}