package com.battagliandrea.galleryappandroid.data.networking

import com.battagliandrea.data.networking.RedditApiDataSource
import com.battagliandrea.data.networking.model.toImages
import com.battagliandrea.domain.exception.CustomException
import com.battagliandrea.domain.model.Image
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RedditApiDataSourceImpl @Inject constructor(
    private val redditApiContract: RedditApiContract
) : RedditApiDataSource {

    override suspend fun getImages(search: String): List<Image> {
        try{
            return redditApiContract.getImages(search = search)
                .let { resp ->
                    if(resp.isSuccessful){
                        return@let resp.body()?.data?.children?.toImages().orEmpty()
                    } else {
                        throw CustomException(errorCode = 1)
                    }
                }
        }catch (e: UnknownHostException){
            throw CustomException(errorCode = 2)
        }
    }

}