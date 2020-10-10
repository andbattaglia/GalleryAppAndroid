package com.battagliandrea.galleryappandroid.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.battagliandrea.galleryappandroid.data.database.dao.BookmarksDao
import com.battagliandrea.galleryappandroid.data.database.entity.BookmarkEntity

@Database(
        entities = [BookmarkEntity::class],
        version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookmarksDao(): BookmarksDao
}