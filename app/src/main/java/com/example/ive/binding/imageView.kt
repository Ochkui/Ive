package com.example.ive.binding

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("app:drawImage")
fun drawImage(view:ImageView,@DrawableRes image:Int){
    view.setImageResource(image)
}