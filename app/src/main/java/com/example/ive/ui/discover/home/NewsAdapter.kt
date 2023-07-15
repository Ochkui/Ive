package com.example.ive.ui.discover.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ive.R
import com.example.ive.component.model.DataNews
import com.example.ive.databinding.RecyclerHorizontalItemBinding
import com.squareup.picasso.Picasso

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private val listItem = mutableListOf<DataNews>()

    class ViewHolder(val binding: RecyclerHorizontalItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listItem: DataNews){
            Picasso.get()
                .load(listItem.imageUrls)
                .into(binding.aiImage)
            binding.vUserProf.setViewData(listItem.user)
        }
    }

    fun submitList(list: List<DataNews>) {
        listItem.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerHorizontalItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listItem[position])
    }
}