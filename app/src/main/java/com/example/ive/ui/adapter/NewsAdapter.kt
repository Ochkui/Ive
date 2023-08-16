package com.example.ive.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ive.component.model.DataNews
import com.example.ive.databinding.RecyclerHorizontalItemBinding
import com.squareup.picasso.Picasso

typealias NewsClickListener<T> = (T) -> Unit

class NewsAdapter(
    private val userProfileListeners: NewsClickListener<DataNews>,
    private val imageListener:NewsClickListener<DataNews>
): PagingDataAdapter<DataNews, NewsAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(
        private val imageListener: NewsClickListener<DataNews>,
        private val userProfileListeners: NewsClickListener<DataNews>,
        private val binding: RecyclerHorizontalItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listItem: DataNews) {
            Picasso.get()
                .load(listItem.imageUrls)
                .into(binding.aiImage)
            binding.vUserProf.setViewData(listItem.user)

            binding.aiImage.setOnClickListener {
                imageListener(listItem)
            }
            binding.vUserProf.setOnClickListener {
                userProfileListeners(listItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerHorizontalItemBinding.inflate(inflater, parent, false)
        return ViewHolder(imageListener,userProfileListeners,binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<DataNews>() {
            override fun areItemsTheSame(oldItem: DataNews, newItem: DataNews): Boolean {
                return oldItem.photoId == newItem.photoId
            }

            override fun areContentsTheSame(oldItem: DataNews, newItem: DataNews): Boolean {
                return oldItem == newItem
            }
        }
    }
}