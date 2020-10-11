package com.battagliandrea.galleryappandroid.ui.adapters.thumbs.model

import com.battagliandrea.domain.model.Image


data class ThumbItem (
    override var id : String = "",
    var title : String = "",
    var imageUrl: String = "",
    var isBookmarked: Boolean = false
) : BaseThumbItem()

fun List<Image>.toThumbsItems(): List<ThumbItem>{
    return this
        .asSequence()
        .filterNotNull()
        .map { it.toThumbItem() }
        .toList()
}

fun Image.toThumbItem(): ThumbItem {
    return ThumbItem(
        id = this.id,
        title = this.title,
        imageUrl = this.imageUrl,
        isBookmarked = this.isBookmarked
    )
}

fun ThumbItem.toImage(): Image {
    return Image(
        id = this.id,
        title = this.title,
        imageUrl = this.imageUrl,
        isBookmarked = this.isBookmarked
    )
}