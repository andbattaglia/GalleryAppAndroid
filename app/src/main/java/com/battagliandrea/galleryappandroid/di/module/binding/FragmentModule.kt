package com.battagliandrea.galleryappandroid.di.module.binding

import com.battagliandrea.galleryappandroid.di.scope.FragmentScope
import com.battagliandrea.galleryappandroid.ui.list.ImageGridFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun imageGridFragment(): ImageGridFragment
}
