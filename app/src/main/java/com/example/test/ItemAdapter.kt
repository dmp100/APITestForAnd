package com.example.test

import Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test.databinding.ItemBinding
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import android.graphics.drawable.Drawable
import android.util.Log

class ItemAdapterAdapter(val profileList: List<Item>) : RecyclerView.Adapter<ItemAdapterAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = profileList[position]
        holder.title.text = item.galTitle

        Log.d("ImageLoading", "Attempting to load image from URL: ${item.galWebImageUrl}")

        Glide.with(holder.itemView.context)
            .load(item.galWebImageUrl)
            .into(holder.image)

    }

    override fun getItemCount(): Int = profileList.size

    inner class Holder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.imageView
        val title = binding.textView
    }
}