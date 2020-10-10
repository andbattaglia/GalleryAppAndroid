package com.battagliandrea.galleryappandroid.ui.adapters.thumbs.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.model.ThumbItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.view_thumb_item.view.*

class ThumbItemVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun render(item: ThumbItem, listener: OnThumbClickListener? = null) = with(itemView) {

        tvTitle.text = item.title

        Glide.with(this)
            .load(item.imageUrl)
            .apply(RequestOptions()
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate())
            .into(iv)

        itemView.setOnClickListener {
            listener?.onItemClick(item)
        }
    }
}