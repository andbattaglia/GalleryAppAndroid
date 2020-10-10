package com.battagliandrea.galleryappandroid.ui.adapters.images.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.battagliandrea.galleryappandroid.R
import com.battagliandrea.galleryappandroid.ui.adapters.images.model.BaseImageItem
import com.battagliandrea.galleryappandroid.ui.adapters.images.model.ImageItem
import com.battagliandrea.galleryappandroid.ui.adapters.images.view.ImageItemVH
import javax.inject.Inject


open class ImagesAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val TYPE_IMAGE = 1
    }

    private var data: MutableList<BaseImageItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_IMAGE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.view_image_item, parent, false)
                ImageItemVH(
                    view
                )
            }
            else -> {
                throw RuntimeException("No supported viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            TYPE_IMAGE -> (holder as ImageItemVH).render(data[position] as ImageItem)
            else -> {
                throw RuntimeException("No supported viewType")
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return data[position].id.hashCode().toLong()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(data[position]){
            is ImageItem -> TYPE_IMAGE
            else -> -1
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //          UTILS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    fun updateList(data: List<BaseImageItem>){
        if(this.data != data){
            val diffCallback =
                ImagesDiffUtils(
                    this.data,
                    data
                )
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            this.data.clear()
            this.data.addAll(data)
            diffResult.dispatchUpdatesTo(this)
        }
    }
}