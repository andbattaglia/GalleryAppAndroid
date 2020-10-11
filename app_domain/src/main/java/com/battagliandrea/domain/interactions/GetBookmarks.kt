package com.battagliandrea.domain.interactions

import com.battagliandrea.domain.model.Image
import com.battagliandrea.domain.repository.ImageRepository
import javax.inject.Inject

class GetBookmarks @Inject constructor(
        private val imageRepository: ImageRepository
){

    suspend operator fun invoke(): List<Image>{
        return imageRepository.getBookmarks()
    }
}


