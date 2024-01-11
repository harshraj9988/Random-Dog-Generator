package com.harshlabs.randomdoggenerator.presentation.recently_generated_dogs_screen

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshlabs.randomdoggenerator.domain.repository.DogImageRepository
import com.harshlabs.randomdoggenerator.presentation.utils.UiState
import com.harshlabs.randomdoggenerator.utils.ResponseResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentlyGeneratedDogsViewModel @Inject constructor(
    private val repository: DogImageRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState<List<Bitmap>>>(UiState.RecentlyGeneratedDogScreen())
    val uiState: LiveData<UiState<List<Bitmap>>> = _uiState

    init {
        getAllPhotos()
    }

    private fun getAllPhotos() {
        viewModelScope.launch {
            repository.getAllPhotos().let {
                if(it is ResponseResource.Success) _uiState.postValue(UiState.ShowData(it.data))
                else if(it is ResponseResource.Error) _uiState.postValue(UiState.ShowToast(it.error))
            }
        }
    }

    fun deleteAllPhotos() {
        viewModelScope.launch {
            repository.deleteAllPhotos().let {
                if(it is ResponseResource.Success) _uiState.postValue(UiState.ShowData(emptyList()))
                else if(it is ResponseResource.Error) _uiState.postValue(UiState.ShowToast(it.error))
            }
        }
    }

    fun goToHomeScreen() {
        _uiState.value = UiState.HomeScreen()
    }
}