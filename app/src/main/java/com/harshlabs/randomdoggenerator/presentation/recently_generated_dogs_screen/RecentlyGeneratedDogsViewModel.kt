package com.harshlabs.randomdoggenerator.presentation.recently_generated_dogs_screen

import androidx.lifecycle.ViewModel
import com.harshlabs.randomdoggenerator.domain.repository.DogImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecentlyGeneratedDogsViewModel @Inject constructor(
    private val repository: DogImageRepository
) : ViewModel() {
}