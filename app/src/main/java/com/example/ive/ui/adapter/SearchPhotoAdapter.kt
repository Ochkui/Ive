package com.example.ive.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ive.databinding.RecyclerVerticalSearchItemBinding
import com.example.ive.network.model.PhotoData
import com.example.ive.ui.listeners.OnclickListeners
import com.squareup.picasso.Picasso

class SearchPhotoAdapter(
    val listener: OnclickListeners<PhotoData>
) : RecyclerView.Adapter<SearchPhotoAdapter.ViewHolder>() {

    private var listPhoto = mutableListOf<PhotoData>()
    private var request:String? = null

    class ViewHolder(
        val listener: OnclickListeners<PhotoData>,
        val binding: RecyclerVerticalSearchItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PhotoData) {
            Picasso.get()
                .load(item.urls.regular)
                .into(binding.aiImage)
            binding.aiImage.setOnClickListener { listener.onClick(item) }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerVerticalSearchItemBinding.inflate(inflater, parent, false)
        return ViewHolder(listener,binding)
    }

    fun submitListWithRequest(list:List<PhotoData>, query: String){
        if (request == null || request != query) {
            request = query
            listPhoto.clear()
        }
        listPhoto.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPhoto[position])
    }

    override fun getItemCount() = listPhoto.size
}