package com.example.appdisplayroadwork.data.repository

import com.example.appdisplayroadwork.core.util.EnumError
import com.example.appdisplayroadwork.core.util.Resource
import com.example.appdisplayroadwork.data.remote.TrafficHazardsApiEndPoint
import com.example.appdisplayroadwork.domain.model.RoadWork
import com.example.appdisplayroadwork.domain.repository.RoadWorkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class FloodRepositoryImpl(
    private val api: TrafficHazardsApiEndPoint
) : RoadWorkRepository {

    override fun getRoadWorkAll(lat: Double, long: Double): Flow<Resource<RoadWork>> = flow {
        emit(Resource.Loading())
        try {
            val roadWordList = api.getRoadWork(lat = lat, long = long)
            emit(Resource.Success(roadWordList.toRoadWork()))

        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    EnumError.SOMETHING_WRONG
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    EnumError.NO_NETWORK
                )
            )
        }
    }

}