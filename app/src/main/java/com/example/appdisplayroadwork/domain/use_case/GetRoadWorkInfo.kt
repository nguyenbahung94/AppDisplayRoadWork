package com.example.appdisplayroadwork.domain.use_case

import com.example.appdisplayroadwork.core.util.Resource
import com.example.appdisplayroadwork.domain.model.RoadWork
import com.example.appdisplayroadwork.domain.repository.RoadWorkRepository
import kotlinx.coroutines.flow.Flow

class GetRoadWorkInfo(
    private val repository: RoadWorkRepository
) {
    operator fun invoke(lat:Double,long:Double): Flow<Resource<RoadWork>> {
        return repository.getRoadWorkAll(lat,long)
    }
}