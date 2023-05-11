package com.example.core.dtos.auth

import android.provider.ContactsContract.CommonDataKinds.Email

data class RegisterUserItem(
    val userName: String,
    val email: String,
    val password: String,
    val ConfirmPassword: String
)
