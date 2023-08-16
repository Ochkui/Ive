package com.example.ive.exstensions

import android.os.Build
import android.os.Parcelable
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.toast(@StringRes massage:Int){
    Toast.makeText(context,massage, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(massage:String){
    Toast.makeText(context,massage, Toast.LENGTH_SHORT).show()
}


inline fun <reified T : Parcelable> Fragment.getParcel(key:String): T? {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arguments?.getParcelable(key,T::class.java)
    } else {
        @Suppress("DEPRECATION")
        arguments?.getParcelable(key)
    }

}

fun Fragment.hideStatusBar() = with(requireActivity()) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.let {
            it.hide(WindowInsets.Type.statusBars())
            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    } else {
        @Suppress("DEPRECATION")
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}

fun Fragment.showStatusBar() = with(requireActivity()) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.show(WindowInsets.Type.statusBars())
    } else {
        @Suppress("DEPRECATION")
        window.clearFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}
