package com.battagliandrea.galleryappandroid.di.module.data


import com.battagliandrea.galleryappandroid.datasource.RedditApiDataSourceImpl
import com.battagliandrea.data.datasource.RedditApiDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class DataSourceModule {

    @Provides
    @Singleton
    fun provideRedditApiDataSource(ds: RedditApiDataSourceImpl): RedditApiDataSource = ds

}
