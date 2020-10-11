package com.battagliandrea.domain.model

data class Image (
    var id: String = "",
    var author : String = "",
    var title : String = "",
    var thumbnailUrl: String = "",
    var imageUrl : String = "",
    var isBookmarked: Boolean = false
)

fun List<Image>.mapBookmarks(bookmarks: List<Image>): List<Image>{
    this.map { image ->  image.isBookmarked = bookmarks.find { it.id == image.id } != null }
    return this
}