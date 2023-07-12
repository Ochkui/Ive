package com.example.ive.exstensions

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes


fun Activity.toast(@StringRes massage:Int){
    Toast.makeText(this,massage, Toast.LENGTH_SHORT).show()
}

fun Activity.toast(massage:String){
    Toast.makeText(this,massage, Toast.LENGTH_SHORT).show()
}