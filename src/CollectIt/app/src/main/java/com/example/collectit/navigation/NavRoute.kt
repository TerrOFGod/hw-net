package com.example.collectit.navigation

import com.example.collectit.R

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
    object LogOut : NavRoute("logout/", R.drawable.logout, "Log Out")
    object Chat : NavRoute("chat/", R.drawable.chat, "Chat")
}