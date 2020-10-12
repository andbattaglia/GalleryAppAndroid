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


class GetBookmarksTest {

    @Mock
    lateinit var imageRepository: ImageRepository

    lateinit var useCase: GetBookmarks

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        useCase = GetBookmarks(imageRepository)
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

            val mockBookmarks : List<Image> = listOf( mock() )

            whenever(imageRepository.getBookmarks()).thenReturn(mockBookmarks)

            useCase()

            verify(imageRepository).getBookmarks()
            verifyNoMoreInteractions(imageRepository)
        }
    }

    /**
     * SCENARIO 2
     *
     * GIVEN usecase
     * WHEN repository catch a CustomException with errorCode 3
     * THEN verify that the error is type of CustomException
     * AND verify that the errorCode is equals to 3
     */
    @Test
    fun `SCENARIO_2`(){
        runBlocking {

            val mockBookmarks : List<Image> = listOf( mock() )

            whenever(imageRepository.getBookmarks()).thenThrow(CustomException(errorCode = 3))

            try{
                useCase
            } catch (e: CustomException){
                assertEquals(e.errorCode, 3)
            }
        }
    }

}

