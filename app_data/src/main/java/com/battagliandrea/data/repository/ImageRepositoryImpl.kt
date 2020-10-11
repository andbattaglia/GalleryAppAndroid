package com.battagliandrea.data.repository

import com.battagliandrea.data.database.RoomDataSource
import com.battagliandrea.data.networking.RedditApiDataSource
import com.battagliandrea.domain.model.Image
import com.battagliandrea.domain.repository.ImageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
open class ImageRepositoryImpl @Inject constructor(
        private val redditApiDataSource: RedditApiDataSource,
        private val roomDataSource: RoomDataSource
) : ImageRepository {

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //          IMAGES
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        private val cache: MutableList<Image> = mutableListOf()

        override suspend fun get(force: Boolean, search: String): List<Image> {
                if(force){
                        redditApiDataSource.getImages(search = search)
                                .let { remoteData ->
                                        cache.clear()
                                        cache.addAll(remoteData)
                                }
                }

                val bookmarks = roomDataSource.getBookmarks()

                cache.map { image ->  image.isBookmarked = bookmarks.find { it.id == image.id } != null }
                return cache
        }

        override fun clearCache() {
                cache.clear()
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //          BOOKMARKS
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        override suspend fun getBookmarks(): List<Image> {
                return roomDataSource.getBookmarks()
                        .map { image ->
                                image.isBookmarked = true
                                return@map image
                        }
        }

        override suspend fun setBookmark(image: Image): Image {
                image.isBookmarked = true

                roomDataSource.insertBookmarks(image)
                cache.map {cachedImage ->
                        if(cachedImage.id == image.id){
                                cachedImage.isBookmarked = true
                        }
                }
                return image
        }

        @ExperimentalCoroutinesApi
        override suspend fun removeBookmark(image: Image): Image {
                image.isBookmarked = false

                roomDataSource.removeBookmarks(image.id)
                cache.map {ic ->
                        if(ic.id == image.id){
                                ic.isBookmarked = false
                        }
                }
                return image
        }
}
