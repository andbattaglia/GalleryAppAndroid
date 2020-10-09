package com.battagliandrea.galleryappandroid.ui.adapters.images.model

import com.battagliandrea.domain.model.Image


data class ImageImageItem (
    override var id : String = "",
    var title : String = "",
    var imageUrl: String = ""
) : BaseImageItem()

fun List<Image>.toImageItems(): List<ImageImageItem>{
    return this
        .asSequence()
        .filterNotNull()
        .map { it.toImageItem() }
        .toList()
}

fun Image.toImageItem(): ImageImageItem {
    return ImageImageItem(
        id = this.id,
        title = this.title,
        imageUrl = this.imageUrl
    )
}