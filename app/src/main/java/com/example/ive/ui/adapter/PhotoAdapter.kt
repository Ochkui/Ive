package com.example.ive.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ive.component.model.DataNews
import com.example.ive.databinding.RecyclerVerticalPhotoBinding
import com.example.ive.ui.listeners.OnclickListeners
import com.squareup.picasso.Picasso

class PhotoAdapter(
    val listener: OnclickListeners<DataNews>
) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    private val listPhoto = mutableListOf<DataNews>()

    class ViewHolder(
        val binding: RecyclerVerticalPhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataNews, listener: OnclickListeners<DataNews>) {
            Picasso.get()
                .load(item.imageUrls)
                .into(binding.aiImage)
            binding.aiImage.setOnClickListener { listener.onClick(item) }
        }
    }

    fun submitList(list: List<DataNews>) {
        listPhoto.addAll(list)
        Log.i("HomeViewModel", "commit list photo")
        notifyDataSetChanged()
    }
    fun removeList(){
        listPhoto.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerVerticalPhotoBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPhoto[position],listener)
    }

    override fun getItemCount() = listPhoto.size
}