package com.battagliandrea.domain.repository

import com.battagliandrea.domain.model.Image
import kotlinx.coroutines.flow.Flow


interface ImageRepository {

    suspend fun observe(): Flow<List<Image>>

    suspend fun pull(search: String, force: Boolean)
}
