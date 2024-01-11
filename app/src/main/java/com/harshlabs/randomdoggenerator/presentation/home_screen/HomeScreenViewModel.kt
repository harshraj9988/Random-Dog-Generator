package com.harshlabs.randomdoggenerator.presentation.home_screen

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
class HomeScreenViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState<Unit>>(UiState.HomeScreen())
    val uiState: LiveData<UiState<Unit>> = _uiState

    fun goToGenerateDogScreen() {
        _uiState.value = UiState.GenerateDogScreen()
    }

    fun goToRecentlyGeneratedDogScreen() {
        _uiState.value = UiState.RecentlyGeneratedDogScreen()
    }

    fun resetUiState() {
        _uiState.value = UiState.HomeScreen()
    }
}