package com.example.ive.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ive.component.model.DataNews
import com.example.ive.databinding.RecyclerVerticalPhotoBinding
import com.example.ive.databinding.RecyclerVerticalSearchItemBinding
import com.example.ive.network.model.PhotoData
import com.example.ive.ui.listeners.OnclickListeners
import com.squareup.picasso.Picasso

class SearchPhotoAdapter(
    val listener: OnclickListeners<PhotoData>
) : RecyclerView.Adapter<SearchPhotoAdapter.ViewHolder>() {

    private var listPhoto = mutableListOf<PhotoData>()

    class ViewHolder(
        val binding: RecyclerVerticalSearchItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PhotoData, listener: OnclickListeners<PhotoData>) {
            Picasso.get()
                .load(item.urls.regular)
                .into(binding.aiImage)
            binding.aiImage.setOnClickListener { listener.onClick(item,1) }
        }
    }

    fun submitList(list: MutableList<PhotoData>) {
        listPhoto = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerVerticalSearchItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPhoto[position],listener)
    }

    override fun getItemCount() = listPhoto.size
}