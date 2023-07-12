package com.example.ive.utils

import android.content.Context
import android.os.Build
import android.view.Window
import android.view.WindowInsets

fun hideStatusBar(window: Window){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.decorView.windowInsetsController?.hide(WindowInsets.Type.navigationBars())
    }
}
