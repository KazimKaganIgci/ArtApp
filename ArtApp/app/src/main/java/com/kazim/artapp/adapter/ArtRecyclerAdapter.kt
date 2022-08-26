package com.kazim.artapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.kazim.artapp.R
import com.kazim.artapp.room.Art
import javax.inject.Inject

class ArtRecyclerAdapter @Inject constructor(
    val glide: RequestManager
):RecyclerView.Adapter<ArtRecyclerAdapter.ArtViewHolder>(){
    class ArtViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)


    private val diffUtil =object :DiffUtil.ItemCallback<Art>(){
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem ==newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem ==newItem
        }

    }

            private val recyclerDiffListener = AsyncListDiffer(this,diffUtil)

            var arts:List<Art>
                get() = recyclerDiffListener.currentList
                set(value) =recyclerDiffListener.submitList(value)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.art_row,parent,false)
        return ArtViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val imageView =holder.itemView.findViewById<ImageView>(R.id.art_Image)
        val nameText =holder.itemView.findViewById<TextView>(R.id.art_nametext)
        val artistText =holder.itemView.findViewById<TextView>(R.id.art_artisttext)
        val yearText =holder.itemView.findViewById<TextView>(R.id.art_yeartext)
        val art =arts[position]
        holder.itemView.apply {
            nameText.text ="NAME:${art.name}"
            artistText.text ="ARTİST NAME:${art.artistName}"
            yearText.text ="YEAR:${art.year}"
            glide.load(art.imageUrl).into(imageView)


        }

    }

    override fun getItemCount(): Int {
        return arts.size

    }
}