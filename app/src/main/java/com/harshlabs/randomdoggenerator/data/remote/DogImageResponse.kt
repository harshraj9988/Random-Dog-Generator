package com.harshlabs.randomdoggenerator.data.remote

import com.google.gson.annotations.SerializedName
import com.harshlabs.randomdoggenerator.domain.models.DogImage
import java.util.Calendar

data class DogImageResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    fun toDogImage(): DogImage {
        return DogImage(
            imageUrl = message,
            timeStamp = Calendar.getInstance().timeInMillis
        )
    }
}
