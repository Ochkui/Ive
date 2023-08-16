package com.example.ive.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ive.component.model.DataNews
import com.example.ive.databinding.RecyclerVerticalPhotoBinding
import com.squareup.picasso.Picasso

class PhotoAdapter(
    val listener: NewsClickListener<DataNews>
) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    private val listPhoto = mutableListOf<DataNews>()

    class ViewHolder(
        val listener: NewsClickListener<DataNews>,
        val binding: RecyclerVerticalPhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataNews) {
            Picasso.get()
                .load(item.imageUrls)
                .into(binding.aiImage)
            binding.aiImage.setOnClickListener { listener(item) }
        }
    }

    fun submitList(list: List<DataNews>) {
        listPhoto.addAll(list)
        // todo improve
        notifyDataSetChanged()
    }
    fun removeList(){
        listPhoto.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerVerticalPhotoBinding.inflate(inflater, parent, false)
        return ViewHolder(listener,binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPhoto[position])
    }

    override fun getItemCount() = listPhoto.size
}