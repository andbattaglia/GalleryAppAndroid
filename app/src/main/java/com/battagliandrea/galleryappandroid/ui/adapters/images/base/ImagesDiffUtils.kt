package com.battagliandrea.galleryappandroid.ui.adapters.images.base

import androidx.recyclerview.widget.DiffUtil
import com.battagliandrea.galleryappandroid.ui.adapters.images.model.BaseImageItem
import com.battagliandrea.galleryappandroid.ui.adapters.images.model.ImageImageItem

class  ImagesDiffUtils(
    private val oldImages: List<BaseImageItem>,
    private val newImages: List<BaseImageItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldImages.size

    override fun getNewListSize(): Int = newImages.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldImages[oldItemPosition].hashCode() == newImages[newItemPosition].hashCode()
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val oldImage = oldImages[oldPosition]
        val newImage = newImages[newPosition]
        return oldImage.id == newImage.id
                && oldImage is ImageImageItem
                && newImage is ImageImageItem
                && oldImage.imageUrl == newImage.imageUrl
                && oldImage.title == newImage.title
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}