package com.battagliandrea.domain.interactions

import com.battagliandrea.domain.repository.ImageRepository
import javax.inject.Inject

class PullBookmark @Inject constructor(
        private val imageRepository: ImageRepository
){

    suspend operator fun invoke(){
        return imageRepository.pullBookmarks()
    }
}


