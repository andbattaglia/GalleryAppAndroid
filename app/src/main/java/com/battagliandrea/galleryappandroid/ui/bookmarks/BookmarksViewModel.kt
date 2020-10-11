package com.battagliandrea.galleryappandroid.ui.bookmarks


import androidx.lifecycle.*
import com.battagliandrea.domain.exception.CustomException
import com.battagliandrea.domain.interactions.ObserveBookmarksStream
import com.battagliandrea.domain.interactions.PullBookmark
import com.battagliandrea.domain.interactions.RemoveBookmark
import com.battagliandrea.galleryappandroid.di.viewmodel.AssistedSavedStateViewModelFactory
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.model.BaseThumbItem
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.model.toThumbsItems
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

open class BookmarksViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val observeBookmarksStream: ObserveBookmarksStream,
    private val pullBookmark: PullBookmark,
    private val removeBookmark: RemoveBookmark
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory : AssistedSavedStateViewModelFactory<BookmarksViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): BookmarksViewModel
    }

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    init {
        _viewState.postValue(ViewState.Initialized)
        observer()
        load()
    }

    @ExperimentalCoroutinesApi
    private fun observer(){
        viewModelScope.launch {
            try {
                observeBookmarksStream().collect { images ->
                    _viewState.postValue(ViewState.BookmarksLoaded(thumbs = images.toThumbsItems()))
                }
            } catch (e: CustomException){
                _viewState.postValue(ViewState.BookmarkError(errorCode = 0))
            }
        }
    }

    fun load(){
        viewModelScope.launch {
            try{
                _viewState.postValue(ViewState.Loading)
                withContext(Dispatchers.Default) { pullBookmark() }
            } catch (e: CustomException){
                _viewState.postValue(ViewState.BookmarkError(errorCode = e.errorCode))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //          VIEW STATE
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    sealed class ViewState {
        object Initialized: ViewState()
        object Loading: ViewState()
        data class BookmarksLoaded(val thumbs: List<BaseThumbItem>): ViewState()
        data class BookmarkError(val errorCode: Int): ViewState()
    }
}
