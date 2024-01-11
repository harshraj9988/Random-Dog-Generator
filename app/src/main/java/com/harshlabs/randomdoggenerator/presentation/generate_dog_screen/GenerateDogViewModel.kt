package com.harshlabs.randomdoggenerator.presentation.generate_dog_screen

import androidx.lifecycle.ViewModel
import com.harshlabs.randomdoggenerator.domain.repository.DogImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GenerateDogViewModel @Inject constructor(
    private val repository: DogImageRepository
) : ViewModel() {
}