package com.example.ive.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding>: AppCompatActivity() {
    protected lateinit var binding:T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,getLayoutId())
        hideStatusBar()
    }

    protected fun hideStatusBar(){
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