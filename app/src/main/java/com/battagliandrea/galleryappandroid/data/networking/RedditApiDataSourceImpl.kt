package com.battagliandrea.galleryappandroid.data.networking

import com.battagliandrea.data.networking.RedditApiDataSource
import com.battagliandrea.data.networking.model.toImages
import com.battagliandrea.domain.model.Image
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RedditApiDataSourceImpl @Inject constructor(
    private val redditApiContract: RedditApiContract
) : RedditApiDataSource {

    override suspend fun getImages(search: String): List<Image> {
        return redditApiContract.getImages(search = search)
            .let {
                //TODO: check error code
                return@let it.body()?.data?.children?.toImages().orEmpty()
            }
    }

}