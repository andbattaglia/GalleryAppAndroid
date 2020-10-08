package com.battagliandrea.galleryappandroid.ui.bookmarks


import androidx.lifecycle.*
import com.battagliandrea.galleryappandroid.di.viewmodel.AssistedSavedStateViewModelFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.*

open class BookmarksViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory : AssistedSavedStateViewModelFactory<BookmarksViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): BookmarksViewModel
    }

    init {
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
