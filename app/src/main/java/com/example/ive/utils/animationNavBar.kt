package com.example.ive.utils

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import com.example.ive.R

fun animationScale(view: View, context: Context){
    val scaleAnimation = AnimationUtils.loadAnimation(context, R.anim.scale_animation)
    view.startAnimation(scaleAnimation)
}