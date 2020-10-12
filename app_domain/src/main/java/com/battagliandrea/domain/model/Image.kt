package com.battagliandrea.domain.model

data class Image (
    var id: String = "",
    var author : String = "",
    var title : String = "",
    var thumbnailUrl: String = "",
    var imageUrl : String = "",
    var isBookmarked: Boolean = false
)