package com.example.appdisplayroadwork.domain.repository

import com.example.appdisplayroadwork.core.util.Resource
import com.example.appdisplayroadwork.domain.model.RoadWork
import kotlinx.coroutines.flow.Flow

interface RoadWorkRepository {

    fun getRoadWorkAll(lat: Double, long: Double): Flow<Resource<RoadWork>>

}