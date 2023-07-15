package com.example.ive.ui.discover.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ive.component.model.DataNews
import com.example.ive.databinding.RecayclerVerticalPhotoBinding
import com.squareup.picasso.Picasso

class PhotoAdapter(
) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    private val listPhoto = mutableListOf<DataNews>()

    class ViewHolder(
        val binding: RecayclerVerticalPhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataNews) {
            Picasso.get()
                .load(item.imageUrls)
                .into(binding.aiImage)
//            binding.aiImage.setImageResource(item.imageUrls)
        }
    }

    fun submitList(list: List<DataNews>) {
        listPhoto.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecayclerVerticalPhotoBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPhoto[position])
    }

    override fun getItemCount() = listPhoto.size
}