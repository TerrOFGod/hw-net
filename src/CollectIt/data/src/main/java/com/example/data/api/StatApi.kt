package com.example.data.api

import retrofit2.Response
import retrofit2.http.*

interface StatApi {
    @GET("traffic")
    suspend fun getStatistics(): Response<Map<Int, Int>>

    @POST("traffic/resource/{resourceId}")
    suspend fun postVisitResource(@Path("resourceId") resourceId: Int)
}