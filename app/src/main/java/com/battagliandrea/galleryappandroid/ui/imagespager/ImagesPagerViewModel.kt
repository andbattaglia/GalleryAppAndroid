package com.battagliandrea.galleryappandroid.ui.imagespager


import androidx.lifecycle.*
import com.battagliandrea.domain.interactions.GetImages
import com.battagliandrea.galleryappandroid.di.viewmodel.AssistedSavedStateViewModelFactory
import com.battagliandrea.galleryappandroid.ui.adapters.images.model.BaseImageItem
import com.battagliandrea.galleryappandroid.ui.adapters.images.model.getPositionById
import com.battagliandrea.galleryappandroid.ui.adapters.images.model.toImageItems
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class ImagesPagerViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getImages: GetImages
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory : AssistedSavedStateViewModelFactory<ImagesPagerViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): ImagesPagerViewModel
    }

    var imageId: String = ""
        set(value) {
            field = value
            load()
        }

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    private fun load(){
        viewModelScope.launch {
            try{
                val images = withContext(Dispatchers.Default) { getImages() }
                _viewState.postValue(ViewState.ImagesLoaded(images = images.toImageItems(), position = images.getPositionById(id = imageId)))
            } catch (e: Exception){
                e.printStackTrace()
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
        data class ImagesLoaded(val images: List<BaseImageItem>, val position: Int): ViewState()
        data class ImageLoadError(val errorType: Int): ViewState()
    }
}
