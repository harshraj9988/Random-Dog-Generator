package com.harshlabs.randomdoggenerator.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshlabs.randomdoggenerator.data.remote.DogGeneratorApi
import com.harshlabs.randomdoggenerator.domain.models.DogImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val dogGeneratorApi: DogGeneratorApi
) : ViewModel() {

    val data = MutableLiveData<DogImage>(DogImage("", 0))

    fun getImage() {
        viewModelScope.launch {
            data.postValue(dogGeneratorApi.getDogImageResponse().toDogImage())
        }
    }
}