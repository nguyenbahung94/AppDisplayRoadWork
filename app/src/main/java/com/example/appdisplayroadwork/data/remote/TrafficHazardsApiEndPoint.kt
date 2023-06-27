package com.example.appdisplayroadwork.data.remote

import com.example.appdisplayroadwork.data.remote.dto.road_work_dto.RoadWorkDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TrafficHazardsApiEndPoint {

    @GET("v1/live/hazards/roadwork/all")
    suspend fun getRoadWork(
        @Header("Authorization") apiKey: String? = ApiClient.API_KEY,
        @Query("lat") lat: Double,
        @Query("long") long: Double
    ): RoadWorkDto
}