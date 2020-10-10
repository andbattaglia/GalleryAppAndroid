package com.battagliandrea.galleryappandroid.ui.adapters.thumbs.view

import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.model.ThumbItem


interface OnThumbClickListener {
    fun onItemClick(thumb: ThumbItem)
}