package com.battagliandrea.galleryappandroid.di.module.framework


import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import com.battagliandrea.galleryappandroid.R
import com.battagliandrea.galleryappandroid.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton


@Module
open class RoomModule {

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //          ROOM
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, context.getString(R.string.app_name) + "-database")
            .build()
}
