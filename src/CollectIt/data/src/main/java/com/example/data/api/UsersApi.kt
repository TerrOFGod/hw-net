package com.example.data.api

import com.example.core.dtos.auth.LoginUserItem
import com.example.core.dtos.auth.RegisterUserItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface UsersApi {
    @POST("mobileRegister")
    suspend fun register(@Body user: RegisterUserItem) : Response<String>

    @POST("mobileLogin")
    suspend fun login(@Body user: LoginUserItem) : Response<String>

    @GET("getUsernameById")
    suspend fun getUsernameById(@Query("userId") userId: String) : Response<String>
}