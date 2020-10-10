package com.battagliandrea.galleryappandroid.ui.adapters.thumbs.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.battagliandrea.galleryappandroid.R
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.model.BaseThumbItem
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.model.LoadingThumbItem
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.model.ThumbItem
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.view.LoadingItemVH
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.view.OnThumbClickListener
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.view.ThumbItemVH
import java.util.*
import javax.inject.Inject


open class ThumbsAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val TYPE_IMAGE = 1
        const val TYPE_LOADER = 2
    }

    var onThumbClickListener: OnThumbClickListener? = null

    private var data: MutableList<BaseThumbItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_IMAGE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.view_thumb_item, parent, false)
                ThumbItemVH(view)
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
            TYPE_IMAGE -> (holder as ThumbItemVH).render(data[position] as ThumbItem, onThumbClickListener)
            TYPE_LOADER -> (holder as LoadingItemVH).render(data[position] as LoadingThumbItem)
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
            is ThumbItem -> TYPE_IMAGE
            is LoadingThumbItem -> TYPE_LOADER
            else -> -1
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //          UTILS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    fun updateList(data: List<BaseThumbItem>){
        if(this.data != data){
            val diffCallback =
                ThumbsDiffUtils(
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
        this.data.clear()
        this.data.add(0, LoadingThumbItem(id = UUID.randomUUID().hashCode().toString()))
        notifyDataSetChanged()
    }

    fun getSpanSize(position: Int): Int{
        return when(getItemViewType(position)){
            TYPE_LOADER -> 3
            else -> 1
        }
    }
}