package com.example.appdisplayroadwork.core.network

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe(): Flow<StatusNetWork>


    enum class StatusNetWork {
        Available, Unavailable, Losing, Lost
    }
}