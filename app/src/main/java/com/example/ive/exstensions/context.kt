package com.example.ive.exstensions

import android.content.Context
import android.os.Build
import android.view.Display
import android.view.WindowManager
import androidx.core.hardware.display.DisplayManagerCompat

val Context.screenWidth: Int
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val defaultDisplay =
            DisplayManagerCompat.getInstance(this).getDisplay(Display.DEFAULT_DISPLAY)
        val displayContext = createDisplayContext(defaultDisplay!!)
        displayContext.resources.displayMetrics.widthPixels
    } else {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.width
    }