package com.battagliandrea.domain.repository

import com.battagliandrea.domain.model.Image
import kotlinx.coroutines.flow.Flow


interface ImageRepository {

    suspend fun get(force: Boolean, search: String = ""): List<Image>

    suspend fun getBookmarks(): List<Image>
    suspend fun setBookmark(image: Image) : Image
    suspend fun removeBookmark(image: Image) : Image

    fun clearCache()
}
