package com.battagliandrea.domain.model

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class ImageTest {

    @Test
    fun image_default() {
        val image = Image()

        assertEquals(image.id, "")
        assertEquals(image.author, "")
        assertEquals(image.title, "")
        assertEquals(image.thumbnailUrl, "")
        assertFalse(image.isBookmarked)
    }
}