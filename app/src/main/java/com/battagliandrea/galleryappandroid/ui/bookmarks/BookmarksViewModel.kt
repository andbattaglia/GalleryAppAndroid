package com.battagliandrea.galleryappandroid.ui.bookmarks


import androidx.lifecycle.*
import com.battagliandrea.domain.exception.CustomException
import com.battagliandrea.domain.interactions.GetBookmarks
import com.battagliandrea.domain.interactions.RemoveBookmark
import com.battagliandrea.galleryappandroid.di.viewmodel.AssistedSavedStateViewModelFactory
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.model.*
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

open class BookmarksViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getBookmarksUseCase: GetBookmarks,
    private val removeBookmarkUseCase: RemoveBookmark
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory : AssistedSavedStateViewModelFactory<BookmarksViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): BookmarksViewModel
    }

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    init {
        _viewState.postValue(ViewState.Initialized)
    }

    fun load(){
        viewModelScope.launch {
            try{
                _viewState.postValue(ViewState.Loading)
                val bookmarks = withContext(Dispatchers.Default) { getBookmarksUseCase() }
                _viewState.postValue(ViewState.BookmarksLoaded(thumbs = bookmarks.toThumbsItems()))
            } catch (e: CustomException){
                _viewState.postValue(ViewState.BookmarkError(errorCode = e.errorCode))
            }
        }
    }

    fun removeBookmark(image: ThumbItem){
        viewModelScope.launch {
            try{
                val updatedImage = withContext(Dispatchers.Default) { removeBookmarkUseCase(image = image.toImage()) }
                _viewState.postValue(ViewState.ChangeImageBookmark(thumb= updatedImage.toThumbItem()))
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
        data class ChangeImageBookmark(val thumb: BaseThumbItem): ViewState()
    }
}
