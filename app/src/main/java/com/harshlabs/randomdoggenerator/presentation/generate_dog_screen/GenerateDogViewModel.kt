package com.harshlabs.randomdoggenerator.presentation.generate_dog_screen

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
class GenerateDogViewModel @Inject constructor(
    private val repository: DogImageRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState<Bitmap>>(UiState.GenerateDogScreen())
    val uiState: LiveData<UiState<Bitmap>> = _uiState

    fun generateImage() {
        viewModelScope.launch {
            repository.downloadAndSaveDogImage().let {
                if (it is ResponseResource.Success) _uiState.postValue(UiState.ShowData(it.data))
                else if (it is ResponseResource.Error) _uiState.postValue(UiState.ShowToast(it.error))
            }
        }
    }

    fun goToHomeScreen() {
        _uiState.postValue(UiState.HomeScreen())
    }
}