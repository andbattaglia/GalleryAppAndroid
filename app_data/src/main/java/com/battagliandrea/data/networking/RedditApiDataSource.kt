package com.battagliandrea.data.networking

import com.battagliandrea.domain.model.Image

interface RedditApiDataSource {

    suspend fun getImages(search: String): List<Image>
}