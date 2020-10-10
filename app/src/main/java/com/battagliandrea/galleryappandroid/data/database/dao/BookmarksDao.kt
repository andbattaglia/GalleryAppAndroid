package com.battagliandrea.galleryappandroid.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.battagliandrea.galleryappandroid.data.database.entity.BookmarkEntity

@Dao
interface BookmarksDao {

    @Query("SELECT * FROM bookmarks")
    suspend fun findAllBookmarks(): List<BookmarkEntity>

    @Query("SELECT * FROM bookmarks WHERE id=:id")
    suspend fun findBookmarkById(id: String): BookmarkEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity) : Long

    @Query("DELETE FROM bookmarks WHERE id=:id")
    suspend fun removeBookmark(id: String) : Int
}