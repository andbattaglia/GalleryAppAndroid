package com.battagliandrea.galleryappandroid.ui.imagesgallery


import androidx.lifecycle.*
import com.battagliandrea.domain.exception.CustomException
import com.battagliandrea.domain.interactions.*
import com.battagliandrea.galleryappandroid.di.viewmodel.AssistedSavedStateViewModelFactory
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.model.*
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

open class ImagesGalleryViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getImagesUseCase: GetImages,
    private val searchImagesUseCase: SearchImages,
    private val saveBookmarkUseCase: SaveBookmark,
    private val removeBookmarkUseCase: RemoveBookmark,
    private val clearCache: ClearCache
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory : AssistedSavedStateViewModelFactory<ImagesGalleryViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): ImagesGalleryViewModel
    }

    private var searchJob: Job? = null

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    init {
        _viewState.postValue(ViewState.Initialized)
    }


    fun load(){
        viewModelScope.launch {
            try{
                val images = withContext(Dispatchers.Default) { getImagesUseCase() }
                if(images.isNotEmpty()){
                    _viewState.postValue(ViewState.ImagesLoaded(thumbs = images.toThumbsItems()))
                } else {
                    _viewState.postValue(ViewState.Initialized)
                }
            } catch (e: CustomException){
                _viewState.postValue(ViewState.Initialized)
            }
        }
    }

    fun search(text: String){
        searchJob?.cancel()

        if(text.length >= 3){
            searchJob = viewModelScope.launch {
                try{
                    delay(300);
                    _viewState.postValue(ViewState.Loading)
                    val images = withContext(Dispatchers.Default) { searchImagesUseCase(search = text) }
                    _viewState.postValue(ViewState.ImagesLoaded(thumbs = images.toThumbsItems()))
                } catch (e: CustomException){
                    _viewState.postValue(ViewState.ImageLoadError(errorCode = e.errorCode))
                }
            }
        } else {
            viewModelScope.launch {
                withContext(Dispatchers.Default) { clearCache()}
            }
            _viewState.postValue(ViewState.Initialized)
        }
    }

    fun saveBookmark(image: ThumbItem){
        viewModelScope.launch {
            try{
                val updatedImage = withContext(Dispatchers.Default) { saveBookmarkUseCase(image = image.toImage()) }
                _viewState.postValue(ViewState.ChangeImageBookmark(thumb= updatedImage.toThumbItem()))
            } catch (e: CustomException){
                _viewState.postValue(ViewState.ImageLoadError(errorCode = e.errorCode))
            }
        }
    }

    fun removeBookmark(image: ThumbItem){
        viewModelScope.launch {
            try{
                val updatedImage = withContext(Dispatchers.Default) { removeBookmarkUseCase(image = image.toImage()) }
                _viewState.postValue(ViewState.ChangeImageBookmark(thumb= updatedImage.toThumbItem()))
            } catch (e: CustomException){
                _viewState.postValue(ViewState.ImageLoadError(errorCode = e.errorCode))
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
    sealed class ViewState() {
        object Initialized: ViewState()
        object Loading: ViewState()
        data class ImagesLoaded(val thumbs: List<BaseThumbItem>): ViewState()
        data class ImageLoadError(val errorCode: Int): ViewState()
        data class ChangeImageBookmark(val thumb: BaseThumbItem): ViewState()
    }
}
