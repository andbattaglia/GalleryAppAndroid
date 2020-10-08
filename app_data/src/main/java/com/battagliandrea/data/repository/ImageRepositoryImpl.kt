package com.battagliandrea.data.repository

import com.battagliandrea.data.datasource.RedditApiDataSource
import com.battagliandrea.domain.repository.ImageRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
open class ImageRepositoryImpl @Inject constructor(
        private val redditApiDataSource: RedditApiDataSource
) : ImageRepository {

}
