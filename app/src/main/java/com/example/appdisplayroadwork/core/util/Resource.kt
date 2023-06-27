package com.example.appdisplayroadwork.core.util

sealed class Resource<T>(val data: T? = null, val message: EnumError? = null) {
    class Loading<T>(data: T? = null): Resource<T>(data)
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message:EnumError, data: T? = null): Resource<T>(data, message)
}

enum class EnumError{
    NO_DATA,NO_NETWORK,SOMETHING_WRONG
}
