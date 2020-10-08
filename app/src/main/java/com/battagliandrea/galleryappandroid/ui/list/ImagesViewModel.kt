package com.battagliandrea.galleryappandroid.ui.list


import androidx.lifecycle.*
import com.battagliandrea.galleryappandroid.di.viewmodel.AssistedSavedStateViewModelFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.*

open class ImagesViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory : AssistedSavedStateViewModelFactory<ImagesViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): ImagesViewModel
    }

    init {
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
