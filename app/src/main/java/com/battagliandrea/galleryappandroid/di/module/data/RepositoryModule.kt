package com.battagliandrea.galleryappandroid.di.module.data



import com.battagliandrea.data.repository.ImageRepositoryImpl
import com.battagliandrea.domain.repository.ImageRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class RepositoryModule {

    @Provides
    @Singleton
    fun provideImageRepository(ds: ImageRepositoryImpl): ImageRepository = ds
}
