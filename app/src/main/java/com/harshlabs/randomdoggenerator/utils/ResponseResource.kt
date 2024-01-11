package com.harshlabs.randomdoggenerator.utils

sealed class ResponseResource<T> {
    data class Success<T>(val data: T) : ResponseResource<T>()
    data class Error<T>(val error: String = "something went wrong") : ResponseResource<T>()
}