package com.example.collectit.screens.account.chat

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DisplayMode.Companion.Input
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.collectit.ui.theme.CollectItTheme

@Composable
fun ChatScreen(
    navController: NavHostController,
    viewModel: ChatViewModel = hiltViewModel(),
    modifier: Modifier
) {
    val context = LocalContext.current

    Log.v("Chat", "Start observe state")
    // State
    val observeState = viewModel.messages.observeAsState()

    LaunchedEffect(Unit) {
        try {
            //viewModel.observeMessages()
        } catch(e: Exception) {
            Toast.makeText(context, "На сервере нет админов. Иди нахуй!!!", Toast.LENGTH_SHORT).show()
            //controller.navigate(Constants.Routes.MUSIC.LIST)
        }
    }

    Column(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 4.dp),
        ) {
            items(observeState.value!!) {
                ChatPageItem(username = it.username, message = it.message)
            }
        }
        Box {
            TextField(
                label = { Text(text = "Пароль") },
                value = viewModel.inputMessage,
                onValueChange = { viewModel.inputMessage = it })
        }
        Box {
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()) {
                Button(onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 5.dp),
                    enabled = viewModel.inputMessage.isNotEmpty()) {
                    Text(text = "Отправить")
                }
                Button(onClick = {}) {
                    Text(text = "П")
                }
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