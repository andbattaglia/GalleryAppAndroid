package com.battagliandrea.galleryappandroid.datasource

import com.battagliandrea.data.datasource.RedditApiDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RedditApiDataSourceImpl @Inject constructor(
    private val redditApiContract: RedditApiContract
) : RedditApiDataSource {

}