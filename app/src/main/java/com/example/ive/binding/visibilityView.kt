package com.example.ive.binding

import android.view.View
import androidx.databinding.BindingAdapter


@BindingAdapter("app:visibility")
fun setVisibility(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}