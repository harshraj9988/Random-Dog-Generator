package com.harshlabs.randomdoggenerator.presentation

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.harshlabs.randomdoggenerator.data.local.InternalStorageManager
import com.harshlabs.randomdoggenerator.data.remote.DogGeneratorApi
import com.harshlabs.randomdoggenerator.domain.models.DogImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val dogGeneratorApi: DogGeneratorApi,
    private val application: BaseApplication,
    private val internalStorageManager: InternalStorageManager
) : ViewModel() {

    val data = MutableLiveData<DogImage>(DogImage("", 0))
    val bitmap = MutableLiveData<Bitmap?>(null)

    fun getImage() {
        viewModelScope.launch {
            data.postValue(dogGeneratorApi.getDogImageResponse().toDogImage())
        }
    }

    fun loadImage() {
        Glide.with(application).asBitmap().load(data.value?.imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    viewModelScope.launch {
                        data.value?.timeStamp?.let {
                            internalStorageManager.savePhotoToInternalStorage(it, resource)
                        }
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })

    }

    fun getImageFromStorage() {
        viewModelScope.launch {
            var dmp: Bitmap? = null
            internalStorageManager.loadAllPhotos().forEach {
               dmp = it.bitmap
            }
            bitmap.postValue(dmp)
        }
    }
}