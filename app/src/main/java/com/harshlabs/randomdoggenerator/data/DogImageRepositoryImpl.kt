package com.harshlabs.randomdoggenerator.data

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.harshlabs.randomdoggenerator.data.local.InternalStorageManager
import com.harshlabs.randomdoggenerator.data.remote.DogGeneratorApi
import com.harshlabs.randomdoggenerator.domain.repository.DogImageRepository
import com.harshlabs.randomdoggenerator.presentation.BaseApplication
import com.harshlabs.randomdoggenerator.utils.ResponseResource
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DogImageRepositoryImpl @Inject constructor(
    private val dogGeneratorApi: DogGeneratorApi,
    private val internalStorageManager: InternalStorageManager,
    private val application: BaseApplication
) : DogImageRepository {
    override suspend fun downloadAndSaveDogImage(): ResponseResource<Bitmap> {
        val bitmapResponse = downloadImage()
        if (bitmapResponse is ResponseResource.Success)
            return internalStorageManager.savePhotoToInternalStorage(bitmapResponse.data)
        return bitmapResponse
    }

    private suspend fun downloadImage(): ResponseResource<Bitmap> =
        suspendCoroutine { continuation ->
            runBlocking {

                val dogImageResponse = try {
                    dogGeneratorApi.getDogImageResponse()
                } catch (e: Exception) {
                    continuation.resume(ResponseResource.Error("unable to connect"))
                    return@runBlocking
                }

                if (dogImageResponse.status != "success") {
                    continuation.resume(ResponseResource.Error())
                    return@runBlocking
                }

                Glide.with(application).asBitmap().load(dogImageResponse.url)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            continuation.resume(ResponseResource.Success(resource))
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            continuation.resume(ResponseResource.Error())
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {}
                    })
            }
        }

    override suspend fun getAllPhotos(): ResponseResource<List<Bitmap>> {
        return internalStorageManager.loadAllPhotos()
    }

    override suspend fun deleteAllPhotos(): ResponseResource<Unit> {
        return internalStorageManager.deleteAllPhotos()
    }
}