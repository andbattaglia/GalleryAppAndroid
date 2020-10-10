package com.battagliandrea.data.database

import com.battagliandrea.domain.model.Image

interface RoomDataSource {

    suspend fun getBookmarks(): List<Image>

    suspend fun insertBookmarks(image: Image): Image

    suspend fun removeBookmarks(id: String): Image
}