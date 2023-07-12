package com.example.ive.exstensions

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.toast(@StringRes massage:Int){
    Toast.makeText(context,massage, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(massage:String){
    Toast.makeText(context,massage, Toast.LENGTH_SHORT).show()
}