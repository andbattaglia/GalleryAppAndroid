package com.battagliandrea.data

import com.battagliandrea.data.database.RoomDataSource
import com.battagliandrea.data.networking.RedditApiDataSource
import com.battagliandrea.data.repository.ImageRepositoryImpl
import com.battagliandrea.domain.exception.CustomException
import com.battagliandrea.domain.model.Image
import com.battagliandrea.domain.repository.ImageRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class ImageRepositoryImplTest {

    @Mock
    lateinit var redditApiDataSource: RedditApiDataSource
    @Mock
    lateinit var roomDataSource: RoomDataSource

    private lateinit var repository: ImageRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        repository = ImageRepositoryImpl(redditApiDataSource, roomDataSource)
    }

    /**
     * SCENARIO 1
     *
     * GIVEN 'get()' method
     * AND a mock collection of images
     * WHEN 'force' param is true
     * THEN verify that method does api call to retrieve images
     * AND there is one interaction with api datasource
     * AND verify that method does database call to retrieve bookmarks
     * AND there is one interaction with database datasource
     */
    @Test
    fun `SCENARIO_1`() {
        runBlocking {

            val mockImages : List<Image> = listOf( mock() )
            val mockBookmarks : List<Image> = listOf( mock() )

            whenever(redditApiDataSource.getImages("search_param")).thenReturn(mockImages)
            whenever(roomDataSource.getBookmarks()).thenReturn(mockBookmarks)

            repository.get(force = true, search = "search_param")

            Mockito.verify(redditApiDataSource).getImages("search_param")
            Mockito.verifyNoMoreInteractions(redditApiDataSource)

            Mockito.verify(roomDataSource).getBookmarks()
            Mockito.verifyNoMoreInteractions(redditApiDataSource)
        }
    }

    /**
     * SCENARIO 2
     * GIVEN 'get()' method
     * AND a mock collection of images
     * WHEN 'force' param is false
     * THEN verify that there is not one interaction with api datasource
     * AND verify that method does database call to retrieve bookmarks
     * AND there is one interaction with database datasource
     */
    @Test
    fun `SCENARIO_2`() {
        runBlocking {

            val mockImages : List<Image> = listOf( mock() )
            val mockBookmarks : List<Image> = listOf( mock() )

            whenever(redditApiDataSource.getImages("search_param")).thenReturn(mockImages)
            whenever(roomDataSource.getBookmarks()).thenReturn(mockBookmarks)

            repository.get(force = false, search = "search_param")

            Mockito.verifyZeroInteractions(redditApiDataSource)

            Mockito.verify(roomDataSource).getBookmarks()
            Mockito.verifyNoMoreInteractions(redditApiDataSource)
        }
    }

    /**
     * SCENARIO 3
     * GIVEN 'get()' method
     * AND a mock collection of images
     * WHEN api datasource catch a CustomException with errorCode 1
     * THEN verify that the error is type of CustomException
     * AND verify that the errorCode is equals to 1
     */
    @Test
    fun `SCENARIO_3`() {
        runBlocking {

            val mockImages : List<Image> = listOf( mock() )
            val mockBookmarks : List<Image> = listOf( mock() )

            whenever(redditApiDataSource.getImages("search_param")).thenThrow(CustomException(errorCode = 1))
            whenever(roomDataSource.getBookmarks()).thenReturn(mockBookmarks)

            try{
                repository.get(force = true, search = "search_param")
            } catch (e: CustomException){
                assertEquals(e.errorCode, 1)
            }
        }
    }

    /**
     * SCENARIO 4
     * GIVEN 'get()' method
     * AND a mock collection of images
     * WHEN database datasource catch a CustomException with errorCode 3
     * THEN verify that the error is type of CustomException
     * AND verify that the errorCode is equals to 3
     */
    @Test
    fun `SCENARIO_4`() {
        runBlocking {

            val mockImages : List<Image> = listOf( mock() )
            val mockBookmarks : List<Image> = listOf( mock() )

            whenever(redditApiDataSource.getImages("search_param")).thenReturn(mockImages)
            whenever(roomDataSource.getBookmarks()).thenThrow(CustomException(errorCode = 3))

            try{
                repository.get(force = true, search = "search_param")
            } catch (e: CustomException){
                assertEquals(e.errorCode, 3)
            }
        }
    }

    /**
     * SCENARIO 5
     * GIVEN 'getBookmarks()' method
     * AND a mock collection of bookmarks
     * THEN verify that there is not one interaction with api datasource
     * AND verify that method does database call to retrieve bookmarks
     * AND there is one interaction with database datasource
     */
    @Test
    fun `SCENARIO_5`() {
        runBlocking {

            val mockBookmarks : List<Image> = listOf( mock() )

            whenever(roomDataSource.getBookmarks()).thenReturn(mockBookmarks)

            repository.getBookmarks()

            Mockito.verify(roomDataSource).getBookmarks()
            Mockito.verifyNoMoreInteractions(redditApiDataSource)
        }
    }

    /**
     * SCENARIO 6
     * GIVEN 'getBookmarks()' method
     * AND a mock collection of bookmarks
     * WHEN database datasource catch a CustomException with errorCode 3
     * THEN verify that the error is type of CustomException
     * AND verify that the errorCode is equals to 3
     */
    @Test
    fun `SCENARIO_6`() {
        runBlocking {

            val mockBookmarks : List<Image> = listOf( mock() )

            whenever(roomDataSource.getBookmarks()).thenThrow(CustomException(errorCode = 3))

            try{
                repository.getBookmarks()
            } catch (e: CustomException){
                assertEquals(e.errorCode, 3)
            }
        }
    }

    /**
     * SCENARIO 7
     * GIVEN 'setBookmark()' method
     * AND a mock image
     * THEN verify that there is not one interaction with database datasource
     */
    @Test
    fun `SCENARIO_7`() {
        runBlocking {

            val image : Image = mock()

            whenever(roomDataSource.insertBookmarks(image)).thenReturn(image)

            repository.setBookmark(image)

            Mockito.verify(roomDataSource).insertBookmarks(image)
            Mockito.verifyNoMoreInteractions(redditApiDataSource)
        }
    }

    /**
     * SCENARIO 8
     * GIVEN 'setBookmark()' method
     * AND a mock image
     * WHEN database datasource catch a CustomException with errorCode 3
     * THEN verify that the error is type of CustomException
     * AND verify that the errorCode is equals to 3
     */
    @Test
    fun `SCENARIO_8`() {
        runBlocking {

            val image : Image = mock()

            whenever(roomDataSource.insertBookmarks(image)).thenThrow(CustomException(errorCode = 3))

            try{
                repository.setBookmark(image)
            } catch (e: CustomException){
                assertEquals(e.errorCode, 3)
            }
        }
    }

    /**
     * SCENARIO 9
     * GIVEN 'removeBookmark()' method
     * AND a mock image
     * THEN verify that there is not one interaction with database datasource
     */
    @Test
    fun `SCENARIO_9`() {
        runBlocking {

            val image : Image = mock()

            whenever(roomDataSource.removeBookmarks(image.id)).thenReturn(image)

            repository.removeBookmark(image)

            Mockito.verify(roomDataSource).removeBookmarks(image.id)
            Mockito.verifyNoMoreInteractions(redditApiDataSource)
        }
    }

    /**
     * SCENARIO 10
     * GIVEN 'removeBookmark()' method
     * AND a mock image
     * WHEN database datasource catch a CustomException with errorCode 3
     * THEN verify that the error is type of CustomException
     * AND verify that the errorCode is equals to 3
     */
    @Test
    fun `SCENARIO_10`() {
        runBlocking {

            val image : Image = mock()

            whenever(roomDataSource.removeBookmarks(image.id)).thenThrow(CustomException(errorCode = 3))

            try{
                repository.removeBookmark(image)
            } catch (e: CustomException){
                assertEquals(e.errorCode, 3)
            }
        }
    }


}