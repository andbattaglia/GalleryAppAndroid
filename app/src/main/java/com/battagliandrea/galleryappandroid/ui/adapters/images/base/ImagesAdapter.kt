package com.battagliandrea.galleryappandroid.ui.adapters.images.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.battagliandrea.galleryappandroid.R
import com.battagliandrea.galleryappandroid.ui.adapters.images.model.BaseImageItem
import com.battagliandrea.galleryappandroid.ui.adapters.images.model.ImageImageItem
import com.battagliandrea.galleryappandroid.ui.adapters.images.model.LoadingImageItem
import com.battagliandrea.galleryappandroid.ui.adapters.images.view.ImageItemVH
import com.battagliandrea.galleryappandroid.ui.adapters.images.view.LoadingItemVH
import com.battagliandrea.galleryappandroid.ui.adapters.images.view.OnImageClickListener
import java.lang.RuntimeException
import java.util.*
import javax.inject.Inject


open class ImagesAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val TYPE_IMAGE = 1
        const val TYPE_LOADER = 2
    }

    var onImageClickListener: OnImageClickListener? = null

    private var data: MutableList<BaseImageItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_IMAGE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.view_image_item, parent, false)
                ImageItemVH(view)
            }
            TYPE_LOADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.view_loading_item, parent, false)
                LoadingItemVH(view)
            }
            else -> {
                throw RuntimeException("No supported viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            TYPE_IMAGE -> (holder as ImageItemVH).render(data[position] as ImageImageItem, onImageClickListener)
            TYPE_LOADER -> (holder as LoadingItemVH).render(data[position] as LoadingImageItem)
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
            is ImageImageItem -> TYPE_IMAGE
            is LoadingImageItem -> TYPE_LOADER
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

    fun showLoadingItem(){
        this.data.add(this.data.size, LoadingImageItem(id = UUID.randomUUID().hashCode().toString()))
        notifyItemChanged(this.data.size)
    }

    fun getSpanSize(position: Int): Int{
        return when(getItemViewType(position)){
            TYPE_LOADER -> 2
            else -> 1
        }
    }
}