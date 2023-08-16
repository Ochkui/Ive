package com.example.ive.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("app:imageUrls")
fun loadImage(view:ImageView, imageUrls:String?){
    Picasso.get()
        .load(imageUrls)
        .into(view)
}