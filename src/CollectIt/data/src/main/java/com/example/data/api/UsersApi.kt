package com.example.data.api

import com.example.core.dtos.auth.LoginUserItem
import com.example.core.dtos.auth.RegisterUserItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UsersApi {
    @POST("mobileRegister")
    suspend fun register(@Body user: RegisterUserItem) : Response<String>

    @POST("mobileLogin")
    suspend fun login(@Body user: LoginUserItem) : Response<String>
}