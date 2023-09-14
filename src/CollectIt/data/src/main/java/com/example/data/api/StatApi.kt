package com.example.data.api

import retrofit2.Response
import retrofit2.http.*

interface StatApi {
    @GET("traffic")
    suspend fun getStatistics(): Response<Map<String, Int>>

    @POST("traffic/resource/{id}")
    suspend fun postVisitResource(@Path("id") id: String)
}