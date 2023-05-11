package com.example.core.dtos.auth

import android.provider.ContactsContract.CommonDataKinds.Email

data class LoginUserItem(
    val email: String,
    val password: String,
    val rememberMe: Boolean
)
