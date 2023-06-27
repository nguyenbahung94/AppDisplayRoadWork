package com.example.appdisplayroadwork.di

import com.example.appdisplayroadwork.data.remote.TrafficHazardsApiEndPoint
import com.example.appdisplayroadwork.data.repository.FloodRepositoryImpl
import com.example.appdisplayroadwork.domain.repository.RoadWorkRepository
import com.example.appdisplayroadwork.domain.use_case.GetRoadWorkInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FloodModule {

    @Provides
    @Singleton
    fun provideGetFlood(repository: RoadWorkRepository): GetRoadWorkInfo {
        return GetRoadWorkInfo(repository)
    }

    @Provides
    @Singleton
    fun provideFloodRepository(api: TrafficHazardsApiEndPoint): RoadWorkRepository {
        return FloodRepositoryImpl(api)
    }
}