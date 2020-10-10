package com.battagliandrea.galleryappandroid.ui.images


import androidx.lifecycle.*
import com.battagliandrea.domain.interactions.ObserveImagesStream
import com.battagliandrea.domain.interactions.SearchImages
import com.battagliandrea.domain.model.Image
import com.battagliandrea.galleryappandroid.di.viewmodel.AssistedSavedStateViewModelFactory
import com.battagliandrea.galleryappandroid.ui.adapters.images.model.BaseImageItem
import com.battagliandrea.galleryappandroid.ui.adapters.images.model.toImageItems
import com.battagliandrea.galleryappandroid.ui.base.ViewState
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.lang.RuntimeException

open class ImagesViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val searchImages: SearchImages,
    private val observeImages: ObserveImagesStream
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory : AssistedSavedStateViewModelFactory<ImagesViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): ImagesViewModel
    }

    private var searchJob: Job? = null

    private val _imagesListVS = MutableLiveData<ViewState<List<BaseImageItem>>>()
    val imagesListVS: LiveData<ViewState<List<BaseImageItem>>> = _imagesListVS

    init {
        observer()
    }

    @ExperimentalCoroutinesApi
    private fun observer(){
        viewModelScope.launch {
            try {
                observeImages().collect { images ->
                    _imagesListVS.postValue(ViewState.Success(data = images.toImageItems()))
                }

            } catch (e: java.lang.Exception){
                _imagesListVS.postValue(ViewState.Error(data = emptyList(), throwable= RuntimeException("Error")))
            }
        }
    }

    fun search(text: String){
        searchJob?.cancel()

        if(text.length > 3){
            searchJob = viewModelScope.launch {
                try{
                    delay(300);
                    _imagesListVS.postValue(ViewState.Loading())
                    withContext(Dispatchers.Default) { searchImages(search = text, force = true) }
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        } else {
            _imagesListVS.postValue(ViewState.Success(data = emptyList()))
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
