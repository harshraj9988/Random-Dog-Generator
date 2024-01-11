package com.harshlabs.randomdoggenerator.presentation

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshlabs.randomdoggenerator.domain.repository.DogImageRepository
import com.harshlabs.randomdoggenerator.utils.ResponseResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
   private val repository: DogImageRepository
) : ViewModel() {

    val bitmap = MutableLiveData<Bitmap?>(null)

    fun loadImage() {
       viewModelScope.launch {
          val response = repository.downloadAndSaveDogImage()
          if(response is ResponseResource.Success) {
             bitmap.postValue(response.data)
          }
       }
    }
}