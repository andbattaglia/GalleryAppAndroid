package com.battagliandrea.galleryappandroid.di.viewmodel

import androidx.lifecycle.ViewModel
import com.abdroid.jrv.core.android.dagger.ViewModelKey
import com.battagliandrea.galleryappandroid.ui.list.ImageGridViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@AssistedModule
@Module(includes=[AssistedInject_BuilderModule::class])
abstract class BuilderModule {


    @Binds
    @IntoMap
    @ViewModelKey(ImageGridViewModel::class)
    abstract fun bindImageGridViewModelFactory(f: ImageGridViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>

}