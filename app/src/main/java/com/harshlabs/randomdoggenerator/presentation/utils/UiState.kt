package com.harshlabs.randomdoggenerator.presentation.utils

sealed class UiState {
    object HomeScreen: UiState()
    object GenerateDogScreen: UiState()
    object RecentlyGeneratedDogScreen: UiState()
    data class ShowToast(val text: String): UiState()
}