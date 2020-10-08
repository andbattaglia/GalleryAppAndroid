package com.battagliandrea.galleryappandroid.di.module.binding

import com.battagliandrea.galleryappandroid.di.scope.FragmentScope
import com.battagliandrea.galleryappandroid.ui.bookmarks.BookmarksFragment
import com.battagliandrea.galleryappandroid.ui.list.ImagesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun imagesFragment(): ImagesFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun bookmarksFragment(): BookmarksFragment
}
