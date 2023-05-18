package com.example.collectit.screens.account

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.request.SuccessResult
import com.example.collectit.navigation.NavRoute
import com.example.collectit.screens.account.login.LoginViewModel
import com.example.collectit.ui.theme.CollectItTheme

@Composable
fun LoginScreen(
    navController: NavHostController,
    onSuccessResult: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    Log.v("Login", "Start observe state")
    // State
    val observeState = viewModel.success.observeAsState()

    if (observeState.value!!){
        onSuccessResult
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {


        Box(modifier = Modifier.fillMaxSize()) {
            ClickableText(
                text = AnnotatedString("Зарегестрироваться здесь"),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp),
                onClick = { navController.navigate(NavRoute.SignUp.path) },
                style = TextStyle(
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline,
                )
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val email = remember { mutableStateOf("") }
            val password = remember { mutableStateOf("") }
            val rememberMe = remember { mutableStateOf(false) }

            Text(
                text = "Войти",
                style = TextStyle(fontSize = 40.sp, fontFamily = androidx.compose.ui.text.font.FontFamily.Cursive)
            )

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = "Почта") },
                value = email.value,
                onValueChange = { email.value = it })

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = "Пароль") },
                value = password.value,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { password.value = it })

            Spacer(modifier = Modifier.height(20.dp))
            Row (verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = rememberMe.value,
                    onCheckedChange = { !rememberMe.value },
                )
                Text(text = "Запомнить меня")
            }


            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    onClick = {
                        Log.v("Login", "API Call")
                        // API call
                        viewModel.login(email.value, password.value, rememberMe.value)


                    },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Войти")
                }
            }
        }
    }
}