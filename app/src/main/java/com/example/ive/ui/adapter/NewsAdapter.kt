package com.example.ive.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ive.component.model.DataNews
import com.example.ive.databinding.RecyclerHorizontalItemBinding
import com.example.ive.ui.listeners.OnclickListeners
import com.squareup.picasso.Picasso

class NewsAdapter(
    private val userProfileListeners: OnclickListeners<DataNews>,
    private val imageListener:OnclickListeners<DataNews>
): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private val listItem = mutableListOf<DataNews>()

    class ViewHolder(
        private val imageListener: OnclickListeners<DataNews>,
        private val userProfileListeners: OnclickListeners<DataNews>,
        private val binding: RecyclerHorizontalItemBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(listItem: DataNews, ){
            Picasso.get()
                .load(listItem.imageUrls)
                .into(binding.aiImage)
            binding.vUserProf.setViewData(listItem.user)

            binding.aiImage.setOnClickListener {
                imageListener.onClick(listItem)
            }
            binding.vUserProf.setOnClickListener {
                userProfileListeners.onClick(listItem)
            }
        }
    }

    fun submitList(list: List<DataNews>) {
        listItem.addAll(list)
        Log.i("HomeViewModel", "commit list news")
        notifyDataSetChanged()
    }

    fun removeList(){
        listItem.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerHorizontalItemBinding.inflate(inflater, parent, false)
        return ViewHolder(imageListener,userProfileListeners,binding)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listItem[position])
    }
}