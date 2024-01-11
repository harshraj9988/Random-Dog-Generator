package com.harshlabs.randomdoggenerator.utils

sealed class ResponseResource<T> {
    class Success<T>(val data: T) : ResponseResource<T>()
    class Error<T>(val error: String = "something went wrong") : ResponseResource<T>()
}