package com.battagliandrea.galleryappandroid.di.module.binding

import com.battagliandrea.galleryappandroid.ui.main.MainActivity
import com.battagliandrea.galleryappandroid.di.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

}
