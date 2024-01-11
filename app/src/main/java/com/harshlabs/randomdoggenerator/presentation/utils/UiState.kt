package com.harshlabs.randomdoggenerator.presentation.utils

sealed class UiState<T> {
    class HomeScreen<T>: UiState<T>()
    class GenerateDogScreen<T>: UiState<T>()
    class RecentlyGeneratedDogScreen<T>: UiState<T>()
    class ShowToast<T>(val text: String): UiState<T>()
    class ShowData<T>(val data: T): UiState<T>()
}