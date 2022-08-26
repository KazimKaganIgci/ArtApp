package com.kazim.artapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.kazim.artapp.R
import com.kazim.artapp.room.Art
import javax.inject.Inject

class ImageRecyclerAdapter @Inject constructor(
    val glide: RequestManager
): RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder>() {
    class ImageViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    private var onItemClickListener:((String) ->Unit) ?=null



    private val diffUtil =object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem ==newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem ==newItem

        }

    }

    private val recyclerDiffListener = AsyncListDiffer(this,diffUtil)

    var images:List<String>
        get() = recyclerDiffListener.currentList
        set(value) =recyclerDiffListener.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.artimage_row,parent,false)
        return ImageViewHolder(view)
    }
    fun setOnItemClickListener(listener:(String)->Unit){
        onItemClickListener =listener
    }


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
       val imageView =holder.itemView.findViewById<ImageView>(R.id.singleView)
        val url =images[position]
        holder.itemView.apply {
            glide.load(url).into(imageView)
            setOnClickListener{
                onItemClickListener?.let {
                    it(url)

                }
            }

        }
    }

    override fun getItemCount(): Int {
        return images.size
    }
}