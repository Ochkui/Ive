package com.example.ive.ui.base

import android.os.Bundle
import android.view.GestureDetector
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import com.example.ive.utils.hideStatusBar

abstract class BaseActivity<T : ViewDataBinding>: AppCompatActivity() {
    protected lateinit var binding:T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,getLayoutId())
//        transparentStatusBar()


        hideStatusBar(window)
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