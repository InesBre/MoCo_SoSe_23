package com.example.barcodebites.feature_Scan.domain.http

sealed class Response<out T> {
    data class Empty<out T>(val value: T?) : Response<T>()
    data class Success<out T>(val value: T) : Response<T>()
    data class Error<out T>(
        val isNetworkError: Boolean,
        val code: Int?,
        val message: String?
    ) : Response<T>()
}