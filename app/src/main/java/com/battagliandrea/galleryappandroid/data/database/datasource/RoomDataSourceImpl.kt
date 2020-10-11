package com.battagliandrea.galleryappandroid.data.database.datasource

import com.battagliandrea.data.database.RoomDataSource
import com.battagliandrea.data.networking.RedditApiDataSource
import com.battagliandrea.data.networking.model.toImages
import com.battagliandrea.domain.exception.CustomException
import com.battagliandrea.domain.model.Image
import com.battagliandrea.galleryappandroid.data.database.AppDatabase
import com.battagliandrea.galleryappandroid.data.database.entity.toBookmark
import com.battagliandrea.galleryappandroid.data.database.entity.toImage
import com.battagliandrea.galleryappandroid.data.database.entity.toImages
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomDataSourceImpl @Inject constructor(
    private val database: AppDatabase
) : RoomDataSource {


    override suspend fun getBookmarks(): List<Image> {
        try{
            val resp =  database.bookmarksDao().findAllBookmarks()
            return resp.toImages()
        } catch (e: Exception){
            throw CustomException(errorCode = 3)
        }
    }

    override suspend fun insertBookmarks(image: Image): Image {
        try{
            return database.bookmarksDao().insertBookmark(image.toBookmark()).let {
                return@let image
            }
        } catch (e: Exception){
            throw CustomException(errorCode = 3)
        }
    }

    override suspend fun removeBookmarks(id: String): Image {
        try{
            return database.bookmarksDao().findBookmarkById(id)
                .let {
                    bookmark -> database.bookmarksDao().removeBookmark( bookmark.id)
                    return@let bookmark.toImage()
                }
        } catch (e: Exception){
            throw CustomException(errorCode = 3)
        }
    }
}