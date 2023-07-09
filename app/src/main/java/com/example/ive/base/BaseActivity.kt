package com.example.ive.base

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding>: AppCompatActivity() {
    protected lateinit var binding:T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,getLayoutId())
//        transparentStatusBar()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.windowInsetsController?.hide(WindowInsets.Type.navigationBars())
        }
    }

    protected fun transparentStatusBar(){
        WindowCompat.setDecorFitsSystemWindows(
            window,false
        )
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected fun showToast(@StringRes massage:Int){
        Toast.makeText(this,massage,Toast.LENGTH_SHORT).show()
    }
}