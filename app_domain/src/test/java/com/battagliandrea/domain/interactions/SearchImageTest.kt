package com.battagliandrea.domain.interactions

import com.battagliandrea.domain.exception.CustomException
import com.battagliandrea.domain.model.Image
import com.battagliandrea.domain.repository.ImageRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class SearchImageTest {

    @Mock
    lateinit var imageRepository: ImageRepository

    lateinit var useCase: SearchImages

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        useCase = SearchImages(imageRepository)
    }


    /**
     * SCENARIO 1
     *
     * GIVEN usecase
     * AND a mock collection of bookmarks
     * THEN there is one interaction with repository
     */
    @Test
    fun `SCENARIO_1`(){
        runBlocking {

            val mockImages : List<Image> = listOf( mock() )

            whenever(imageRepository.get(force = true, search = "search_param")).thenReturn(mockImages)

            useCase("search_param")

            verify(imageRepository).get(force = true, search = "search_param")
            verifyNoMoreInteractions(imageRepository)
        }
    }

    /**
     * SCENARIO 2
     *
     * GIVEN usecase
     * WHEN repository catch a CustomException with errorCode 1
     * THEN verify that the error is type of CustomException
     * AND verify that the errorCode is equals to 1
     */
    @Test
    fun `SCENARIO_2`(){
        runBlocking {

            val mockImages : List<Image> = listOf( mock() )

            whenever(imageRepository.get(force = true, search = "search_param")).thenThrow(CustomException(errorCode = 1))

            try{
                useCase("search_param")
            } catch (e: CustomException){
                assertEquals(e.errorCode, 1)
            }
        }
    }

}

