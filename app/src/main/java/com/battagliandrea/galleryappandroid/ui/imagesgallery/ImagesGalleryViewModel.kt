package com.battagliandrea.galleryappandroid.ui.imagesgallery


import androidx.lifecycle.*
import com.battagliandrea.domain.exception.CustomException
import com.battagliandrea.domain.interactions.ObserveImagesStream
import com.battagliandrea.domain.interactions.SearchImages
import com.battagliandrea.galleryappandroid.di.viewmodel.AssistedSavedStateViewModelFactory
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.model.BaseThumbItem
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.model.toThumbsItems
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

open class ImagesGalleryViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val searchImages: SearchImages,
    private val observeImages: ObserveImagesStream
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
        observer()
    }

    @ExperimentalCoroutinesApi
    private fun observer(){
        viewModelScope.launch {
            try {
                observeImages().collect { images ->
                    _viewState.postValue(ViewState.ImagesLoaded(thumbs = images.toThumbsItems()))
                }
            } catch (e: CustomException){
                _viewState.postValue(ViewState.ImageLoadError(errorCode = 0))
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
                    withContext(Dispatchers.Default) { searchImages(search = text, force = true) }
                } catch (e: CustomException){
                    _viewState.postValue(ViewState.ImageLoadError(errorCode = e.errorCode))
                }
            }
        } else {
            _viewState.postValue(ViewState.Initialized)
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
    }
}
