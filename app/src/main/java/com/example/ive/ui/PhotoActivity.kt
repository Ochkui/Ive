package com.example.ive.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.compose.ui.res.colorResource
import androidx.core.content.ContextCompat
import com.example.ive.R
import com.example.ive.binding.loadImage
import com.example.ive.component.model.DataNews
import com.example.ive.databinding.PhotoActivityBinding
import com.example.ive.exstensions.hideStatusBar
import com.example.ive.exstensions.toast
import com.example.ive.ui.base.BaseActivity
import com.squareup.picasso.Picasso

class PhotoActivity: BaseActivity<PhotoActivityBinding>() {

    private var photo:DataNews? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras

        if (bundle != null && bundle.containsKey("data")) {
            photo = bundle.getParcelable("data")
        } else {
            toast("Not photo")
            finish()
        }
        hideStatusBar()
        initView()
        initListeners()
    }

    private fun initView() {
        Picasso.get()
            .load(photo?.imageUrls)
            .into(binding.ibImage)

        with(binding.vUserProf) {
            setViewData(photo?.user!!)
            binding.vUserProf.setTextColor(ContextCompat.getColor(this@PhotoActivity,R.color.white))
        }

    }

    private fun initListeners() {
        binding.ibExit.setOnClickListener{
            finish()
        }
    }

    override fun getLayoutId() = R.layout.photo_activity

}