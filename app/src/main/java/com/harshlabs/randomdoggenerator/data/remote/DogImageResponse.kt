package com.harshlabs.randomdoggenerator.data.remote

import com.google.gson.annotations.SerializedName

data class DogImageResponse(
    @SerializedName("message")
    val url: String,
    @SerializedName("status")
    val status: String
)