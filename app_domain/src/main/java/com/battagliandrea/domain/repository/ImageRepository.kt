package com.battagliandrea.domain.repository

import com.battagliandrea.domain.model.Image
import kotlinx.coroutines.flow.Flow


interface ImageRepository {

    suspend fun observe(): Flow<List<Image>>

    suspend fun pull(search: String, force: Boolean)

    suspend fun get() : List<Image>

    suspend fun observeBookmarks(): Flow<List<Image>>

    suspend fun pullBookmarks()

    suspend fun setBookmark(id: String) : Image

    suspend fun removeBookmark(id: String) : Image
}
