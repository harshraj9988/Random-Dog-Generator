package com.harshlabs.randomdoggenerator.data.remote

import retrofit2.http.GET

interface DogGeneratorApi {
    @GET(END_POINT)
    suspend fun getDogImageResponse(): DogImageResponse
}