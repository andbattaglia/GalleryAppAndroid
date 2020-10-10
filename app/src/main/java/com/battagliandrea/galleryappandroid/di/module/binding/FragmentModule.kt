package com.battagliandrea.galleryappandroid.di.module.binding

import com.battagliandrea.galleryappandroid.di.scope.FragmentScope
import com.battagliandrea.galleryappandroid.ui.bookmarks.BookmarksFragment
import com.battagliandrea.galleryappandroid.ui.imagesgallery.ImagesGalleryFragment
import com.battagliandrea.galleryappandroid.ui.imagespager.ImagesPagerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun imagesGalleryFragment(): ImagesGalleryFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun imagesPagerFragment(): ImagesPagerFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun bookmarksFragment(): BookmarksFragment
}
