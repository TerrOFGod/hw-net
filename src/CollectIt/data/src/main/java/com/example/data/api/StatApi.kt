package com.example.data.api

import retrofit2.Response
import retrofit2.http.*

interface StatApi {
    @GET("traffic")
    suspend fun getStatistics(): Response<Map<String, Int>>

    @POST("traffic/image/{id}")
    suspend fun postVisitImage(@Path("id") id: String)

    @POST("traffic/music/{id}")
    suspend fun postVisitMusic(@Path("id") id: String)

    @POST("traffic/video/{id}")
    suspend fun postVisitVideo(@Path("id") id: String)
}