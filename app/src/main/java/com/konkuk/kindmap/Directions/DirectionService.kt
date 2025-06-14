package com.konkuk.kindmap.Directions

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface DirectionsService {
    @GET("driving")
    fun getDrivingRoute(
        @Header("X-NCP-APIGW-API-KEY-ID") clientId: String,
        @Header("X-NCP-APIGW-API-KEY") clientSecret: String,
        @Query("start") start: String,
        @Query("goal") goal: String
    ): Call<DirectionsResponse>
}

