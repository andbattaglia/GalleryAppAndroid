package com.battagliandrea.galleryappandroid.ui.adapters.images.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.battagliandrea.galleryappandroid.ui.adapters.images.model.ImageImageItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.view_image_item.view.*

class ImageItemVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun render(item: ImageImageItem, listener: OnImageClickListener? = null) = with(itemView) {

        tvTitle.text = item.title

        Glide.with(this)
            .load(item.imageUrl)
            .apply(RequestOptions()
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontAnimate())
            .into(iv)

        itemView.setOnClickListener {
            listener?.onItemClick()
        }
    }
}