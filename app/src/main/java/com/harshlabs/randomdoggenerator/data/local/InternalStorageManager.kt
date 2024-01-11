package com.harshlabs.randomdoggenerator.data.local

import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.harshlabs.randomdoggenerator.presentation.BaseApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileFilter
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class InternalStorageManager @Inject constructor(
    private val application: BaseApplication
) {

    suspend fun loadAllPhotos(): List<StoredDogImage> {
        return withContext(Dispatchers.IO) {
            val files =
                application.filesDir.listFiles(FileFilter { it.canRead() && it.name.endsWith(".jpg") })
            files?.map {
                val bytes = it.readBytes()
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                StoredDogImage(bitmap, it.name)
            } ?: emptyList()
        }
    }

    suspend fun deleteAllPhotos(photos: List<StoredDogImage>) {
        return withContext(Dispatchers.IO) {
            photos.forEach { deletePhoto(it.filename) }
        }
    }


    suspend fun savePhotoToInternalStorage(timeStamp: Long, bitmap: Bitmap): Boolean {
        return try {
            val filesData = getFilesCountWithMinFileName()
            if (filesData.fileCount == 20) deletePhoto(filesData.firstAddedFile)
            application.openFileOutput("$timeStamp.jpg", MODE_PRIVATE).use { stream ->
                val isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                if (!isSuccess) return false
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    private suspend fun getFilesCountWithMinFileName(): FilesData {
        return withContext(Dispatchers.IO) {
            val files = application
                .filesDir
                .listFiles(FileFilter { it.canRead() && it.name.endsWith(".jpg") })

            if (files.isNullOrEmpty()) return@withContext FilesData(0, "")
            return@withContext FilesData(
                files.size,
                files.reduce { acc, file ->
                    if (acc.lastModified() < file.lastModified()) acc else file }.name
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

    data class FilesData(val fileCount: Int, val firstAddedFile: String)
}