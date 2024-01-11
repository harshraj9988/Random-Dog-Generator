package com.harshlabs.randomdoggenerator.domain.repository

import android.graphics.Bitmap
import com.harshlabs.randomdoggenerator.utils.ResponseResource

interface DogImageRepository {

    suspend fun downloadAndSaveDogImage() : ResponseResource<Bitmap>

    suspend fun getAllPhotos(): ResponseResource<List<Bitmap>>

    suspend fun deleteAllPhotos(): ResponseResource<Unit>
}