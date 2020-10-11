package com.battagliandrea.data.repository

import com.battagliandrea.data.database.RoomDataSource
import com.battagliandrea.data.networking.RedditApiDataSource
import com.battagliandrea.domain.model.Image
import com.battagliandrea.domain.repository.ImageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
        private val imagesCache: MutableList<Image> = mutableListOf()

        @ExperimentalCoroutinesApi
        private val imagesChannel: ConflatedBroadcastChannel<List<Image>> = ConflatedBroadcastChannel()

        @ExperimentalCoroutinesApi
        override suspend fun observe(): Flow<List<Image>> {
                return imagesChannel.asFlow()
        }

        @ExperimentalCoroutinesApi
        override suspend fun pull(search: String, force: Boolean) {
                if(force){
                        imagesCache.clear()
                }

                return redditApiDataSource.getImages(search = search)
                        .let { remoteData ->
                                imagesCache.addAll(remoteData)
                                imagesChannel.send(imagesCache)
                        }
        }

        override suspend fun get(): List<Image> {
                return imagesCache
        }


        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //          BOOKMARKS
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        @ExperimentalCoroutinesApi
        private val bookmarksChannel: ConflatedBroadcastChannel<List<Image>> = ConflatedBroadcastChannel()

        @ExperimentalCoroutinesApi
        override suspend fun observeBookmarks(): Flow<List<Image>> {
               return bookmarksChannel.asFlow()
        }

        @ExperimentalCoroutinesApi
        override suspend fun pullBookmarks() {
                return roomDataSource.getBookmarks()
                        .let { localData ->
                                bookmarksChannel.send(localData)
                        }
        }

        @ExperimentalCoroutinesApi
        override suspend fun setBookmark(id: String): Image {
                val image = imagesCache.first { image -> image.id == id }
                roomDataSource.insertBookmarks(image)

                val bookmarks = roomDataSource.getBookmarks()
                bookmarksChannel.send(bookmarks)

                return image
        }

        @ExperimentalCoroutinesApi
        override suspend fun removeBookmark(id: String): Image {
                val image = roomDataSource.removeBookmarks(id)

                val bookmarks = roomDataSource.getBookmarks()
                bookmarksChannel.send(bookmarks)

                return image
        }
}
