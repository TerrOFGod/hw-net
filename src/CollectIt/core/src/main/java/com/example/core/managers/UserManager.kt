package com.example.core.managers

import android.provider.ContactsContract.CommonDataKinds.Email
import com.example.core.dtos.auth.Result

interface UserManager {
    suspend fun register(username: String, email: String, password: String, confirm: String) : Result<String,String>
    suspend fun login(email: String, password: String, remember: Boolean) : Result<String,String>
}