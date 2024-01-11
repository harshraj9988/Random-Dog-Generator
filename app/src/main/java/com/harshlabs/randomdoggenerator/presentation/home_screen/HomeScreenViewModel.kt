package com.harshlabs.randomdoggenerator.presentation.home_screen

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
class HomeScreenViewModel @Inject constructor(
) : ViewModel() {

}