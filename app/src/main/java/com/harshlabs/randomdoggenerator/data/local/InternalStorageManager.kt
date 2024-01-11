package com.harshlabs.randomdoggenerator.data.local

import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.harshlabs.randomdoggenerator.presentation.BaseApplication
import com.harshlabs.randomdoggenerator.utils.ResponseResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.FileFilter
import java.util.UUID
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class InternalStorageManager @Inject constructor(
    private val application: BaseApplication
) {

    suspend fun loadAllPhotos(): ResponseResource<List<Bitmap>> = suspendCoroutine{continuation ->
        runBlocking {
            withContext(Dispatchers.IO) {
                val files =
                    application.filesDir.listFiles(FileFilter { it.canRead() && it.name.endsWith(".jpg") })
                        ?.sortedByDescending { it.lastModified() }

                val storedDogImages = files?.map {
                    val bytes = it.readBytes()
                     BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                } ?: emptyList()

                continuation.resume(ResponseResource.Success(storedDogImages))
            }
        }
    }


    suspend fun savePhotoToInternalStorage(bitmap: Bitmap): ResponseResource<Bitmap> =
        suspendCoroutine { continuation ->
            runBlocking {
                try {
                    val filesData = getFilesCountWithMinFileName()
                    if (filesData.fileCount == 20) deletePhoto(filesData.firstAddedFile)
                    application.openFileOutput("${UUID.randomUUID()}.jpg", MODE_PRIVATE)
                        .use { stream ->
                            val isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                            if (!isSuccess) {
                                continuation.resume(ResponseResource.Error())
                                return@runBlocking
                            }
                            continuation.resume(ResponseResource.Success(bitmap))
                        }
                } catch (e: Exception) {
                    e.printStackTrace()
                    continuation.resume(ResponseResource.Error())
                }
            }
        }

    private suspend fun getFilesCountWithMinFileName(): FilesData {
        return withContext(Dispatchers.IO) {
            val files = application
                .filesDir
                .listFiles(FileFilter { it.canRead() && it.name.endsWith(".jpg") })

            if (files.isNullOrEmpty()) FilesData(0, "")
            else FilesData(
                files.size,
                files.reduce { acc, file ->
                    if (acc.lastModified() < file.lastModified()) acc else file
                }.name
            )
        }
    }

    private fun deletePhoto(filename: String): Boolean {
        return try {
            application.deleteFile(filename)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun deleteAllPhotos(): ResponseResource<Unit> = suspendCoroutine{continuation ->
        runBlocking {
            withContext(Dispatchers.IO) {
                val files = application
                    .filesDir
                    .listFiles(FileFilter { it.canRead() && it.name.endsWith(".jpg") })
                files.forEach { deletePhoto(it.name) }
                continuation.resume(ResponseResource.Success(Unit))
            }
        }
    }

    data class FilesData(val fileCount: Int, val firstAddedFile: String)
}