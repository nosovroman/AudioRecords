package com.example.vkrecorder.common

sealed class NetState<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : NetState<T>(data)
    class Error<T>(message: String, data: T? = null) : NetState<T>(message = message, data = data)
    class Loading<T>(data: T? = null) : NetState<T>(data)
}
