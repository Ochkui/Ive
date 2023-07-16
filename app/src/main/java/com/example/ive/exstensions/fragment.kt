package com.example.ive.exstensions

import android.os.Build
import android.os.Parcelable
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.ive.component.model.UserProfileViewData

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