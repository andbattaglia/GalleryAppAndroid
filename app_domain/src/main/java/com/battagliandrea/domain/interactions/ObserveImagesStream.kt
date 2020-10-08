package com.battagliandrea.domain.interactions

import com.battagliandrea.domain.model.Image
import com.battagliandrea.domain.repository.ImageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ObserveImagesStream @Inject constructor(
        private val imageRepository: ImageRepository
){

    @ExperimentalCoroutinesApi
    suspend operator fun invoke() : Flow<List<Image>>{
            return imageRepository.observe()
    }
}


