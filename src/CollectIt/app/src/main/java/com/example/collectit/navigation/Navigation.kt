package com.example.collectit.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.collectit.R
import com.example.collectit.navigation.destination.account.add
import com.example.collectit.navigation.destination.account.login
import com.example.collectit.navigation.destination.account.profile
import com.example.collectit.navigation.destination.account.signup
import com.example.collectit.navigation.destination.home
import com.example.collectit.navigation.destination.resources.images.image
import com.example.collectit.navigation.destination.resources.images.images
import com.example.collectit.navigation.destination.resources.music.music
import com.example.collectit.navigation.destination.resources.video.video

sealed class NavRoute(var path: String, var icon: Int?, var title: String) {
    object Home : NavRoute("home/", R.drawable.home_48, "Home")
    object Images : NavRoute("images/", R.drawable.image_48, "Images")
    object Image : NavRoute("images/", null, "Images")
    object Music : NavRoute("music/", R.drawable.music_48, "Music")
    object Video : NavRoute("video/", R.drawable.video_48, "Video")
    object Profile : NavRoute("profile/", R.drawable.person_48, "Profile")
    object Add : NavRoute("add/", R.drawable.add_48, "Add")
    object Login : NavRoute("login/", null, "Login")
    object SignUp : NavRoute("signup/", null, "Sign Up")
    object LogOut : NavRoute("logout/", null, "Log Out")
}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Composable
fun CollectItNavHost(
    navController: NavHostController
){
    NavHost(navController = navController, startDestination = NavRoute.Home.path){
        home(navController)

        images(navController)
        image(navController)

        music(navController)

        video(navController)

        profile(navController)
        add(navController)

        login(navController)
        signup(navController)
    }
}

