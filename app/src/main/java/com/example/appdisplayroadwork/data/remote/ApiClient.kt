package com.example.appdisplayroadwork.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {
        private const val BASE_URL = "https://api.transport.nsw.gov.au/"
        const val API_KEY="apikey mvRqP7xe2dstf7n7flBRIItulczduBLV2OV3"
        fun getService(): TrafficHazardsApiEndPoint {
            val client = OkHttpClient.Builder()
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            val build = Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return build.create(TrafficHazardsApiEndPoint::class.java)
        }
    }
}