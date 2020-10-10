package com.battagliandrea.galleryappandroid.ui.adapters.thumbs.base

import androidx.recyclerview.widget.DiffUtil
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.model.BaseThumbItem
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.model.ThumbItem

class  ThumbsDiffUtils(
    private val oldThumbs: List<BaseThumbItem>,
    private val newThumbs: List<BaseThumbItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldThumbs.size

    override fun getNewListSize(): Int = newThumbs.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldThumbs[oldItemPosition].hashCode() == newThumbs[newItemPosition].hashCode()
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val oldImage = oldThumbs[oldPosition]
        val newImage = newThumbs[newPosition]
        return oldImage.id == newImage.id
                && oldImage is ThumbItem
                && newImage is ThumbItem
                && oldImage.imageUrl == newImage.imageUrl
                && oldImage.title == newImage.title
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}