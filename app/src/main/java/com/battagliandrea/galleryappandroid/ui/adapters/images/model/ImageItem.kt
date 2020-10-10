package com.battagliandrea.galleryappandroid.ui.adapters.images.model

import com.battagliandrea.domain.model.Image


data class ImageItem (
    override var id : String = "",
    var title : String = "",
    var imageUrl: String = "",
    var author: String = ""
) : BaseImageItem()

fun List<Image>.getPositionById(id: String): Int{
    var position =  0
    this.forEachIndexed { index, image ->
        if(id == image.id){
            position = index
        }
    }
    return position
}

fun List<Image>.toImageItems(): List<ImageItem>{
    return this
        .asSequence()
        .filterNotNull()
        .map { it.toImageItem() }
        .toList()
}

fun Image.toImageItem(): ImageItem {
    return ImageItem(
        id = this.id,
        title = this.title,
        author = this.author,
        imageUrl = this.imageUrl
    )
}