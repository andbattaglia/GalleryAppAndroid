package com.battagliandrea.domain.interactions

import com.battagliandrea.domain.model.Image
import com.battagliandrea.domain.repository.ImageRepository
import javax.inject.Inject

class SearchImages @Inject constructor(
        private val imageRepository: ImageRepository
){

    suspend operator fun invoke(search: String): List<Image>{
        return imageRepository.get(force = true, search= search)
    }
}


