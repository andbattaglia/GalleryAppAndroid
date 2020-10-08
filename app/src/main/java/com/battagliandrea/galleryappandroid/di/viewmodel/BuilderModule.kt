package com.battagliandrea.galleryappandroid.di.viewmodel

import androidx.lifecycle.ViewModel
import com.abdroid.jrv.core.android.dagger.ViewModelKey
import com.battagliandrea.galleryappandroid.ui.bookmarks.BookmarksViewModel
import com.battagliandrea.galleryappandroid.ui.list.ImagesViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@AssistedModule
@Module(includes=[AssistedInject_BuilderModule::class])
abstract class BuilderModule {

    @Binds
    @IntoMap
    @ViewModelKey(ImagesViewModel::class)
    abstract fun bindImagesViewModelFactory(f: ImagesViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>

    @Binds
    @IntoMap
    @ViewModelKey(BookmarksViewModel::class)
    abstract fun bindBookmarksViewModelFactory(f: BookmarksViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>

}