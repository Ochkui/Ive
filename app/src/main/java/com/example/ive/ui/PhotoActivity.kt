package com.example.ive.ui

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.GestureDetectorCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.ive.R
import com.example.ive.component.model.DataNews
import com.example.ive.databinding.PhotoActivityBinding
import com.example.ive.exstensions.hideStatusBar
import com.example.ive.exstensions.toast
import com.example.ive.ui.base.BaseActivity
import com.example.ive.ui.discover.profile.ProfileFragment
import com.squareup.picasso.Picasso

class PhotoActivity: BaseActivity<PhotoActivityBinding>() {

    private var photo:DataNews? = null

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

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
        binding.vUserProf.setOnClickListener {

            toast("Profile")

            val navController = findNavController(R.id.nav_container_discover)
            navController.navigate(R.id.profileFragment, bundleOf("data" to photo?.user))

        }
    }

    override fun getLayoutId() = R.layout.photo_activity

}