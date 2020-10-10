package com.battagliandrea.galleryappandroid.di.viewmodel

import androidx.lifecycle.ViewModel
import com.abdroid.jrv.core.android.dagger.ViewModelKey
import com.battagliandrea.galleryappandroid.ui.bookmarks.BookmarksViewModel
import com.battagliandrea.galleryappandroid.ui.imagesgallery.ImagesGalleryViewModel
import com.battagliandrea.galleryappandroid.ui.imagespager.ImagesPagerViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@AssistedModule
@Module(includes=[AssistedInject_BuilderModule::class])
abstract class BuilderModule {

    @Binds
    @IntoMap
    @ViewModelKey(ImagesGalleryViewModel::class)
    abstract fun bindImagesViewModelFactory(f: ImagesGalleryViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>

    @Binds
    @IntoMap
    @ViewModelKey(ImagesPagerViewModel::class)
    abstract fun bindImagesPagerViewModelFactory(f: ImagesPagerViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>

    @Binds
    @IntoMap
    @ViewModelKey(BookmarksViewModel::class)
    abstract fun bindBookmarksViewModelFactory(f: BookmarksViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>

}