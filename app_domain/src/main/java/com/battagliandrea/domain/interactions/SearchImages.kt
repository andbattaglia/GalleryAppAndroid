package com.battagliandrea.domain.interactions

import com.battagliandrea.domain.repository.ImageRepository
import javax.inject.Inject

class SearchImages @Inject constructor(
        private val imageRepository: ImageRepository
){

    suspend operator fun invoke(search: String, force: Boolean){
        return imageRepository.pull(search= search, force = force)
    }
}


