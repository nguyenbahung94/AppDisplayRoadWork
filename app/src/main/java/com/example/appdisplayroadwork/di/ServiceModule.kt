package com.example.appdisplayroadwork.di

import com.example.appdisplayroadwork.data.remote.ApiClient
import com.example.appdisplayroadwork.data.remote.TrafficHazardsApiEndPoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideTrafficHazardService(): TrafficHazardsApiEndPoint = ApiClient.getService()

    @Provides
    fun providersIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}