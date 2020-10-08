package com.battagliandrea.data.repository

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
        private val redditApiDataSource: RedditApiDataSource
) : ImageRepository {

        private val imagesCache: MutableList<Image> = mutableListOf()

        @ExperimentalCoroutinesApi
        private val imagesChannel: ConflatedBroadcastChannel<List<Image>> = ConflatedBroadcastChannel()

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

}
