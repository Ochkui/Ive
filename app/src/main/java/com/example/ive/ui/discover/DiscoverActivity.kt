package com.example.ive.ui.discover

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.ive.R
import com.example.ive.databinding.DiscaverActivityBinding
import com.example.ive.exstensions.toast
import com.example.ive.ui.base.BaseActivity
import com.example.ive.utils.animationScale
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DiscoverActivity : BaseActivity<DiscaverActivityBinding>(), IProgressVisibility,INavigationBarVisibility {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var btHome: ImageButton
    private lateinit var btSearch: ImageButton
    private lateinit var btAdd: ImageButton
    private lateinit var btChat: ImageButton
    private lateinit var btProfile: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI()
        initView()
        initListeners()
    }

    private fun hideSystemUI() {

        window.decorView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    or View.SYSTEM_UI_FLAG_IMMERSIVE
        )
    }

    private fun showSystemUI() {
        window.decorView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        )
    }

    private fun initView() {

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_container_discover) as NavHostFragment
        navController = navHostFragment.navController

        with(binding) {
            btHome = includeNavBar.ibHome
            btSearch = includeNavBar.ibSearch
            btAdd = includeNavBar.ibAdd
            btChat = includeNavBar.ibChats
            btProfile = includeNavBar.ibProfile
        }

    }

    private fun initListeners() {

        btHome.setOnClickListener {
            animationScale(it, this@DiscoverActivity)
            navigate(R.id.discoverFragment)
        }

        btSearch.setOnClickListener {
            animationScale(it, this@DiscoverActivity)
            navigate(R.id.searchFragment)
        }

        btAdd.setOnClickListener {
            animationScale(it, this@DiscoverActivity)
            toast("Add")
        }
        btChat.setOnClickListener {
            animationScale(it, this@DiscoverActivity)
            toast("Chats")
        }
        btProfile.setOnClickListener {
            animationScale(it, this@DiscoverActivity)
            toast("Profile")
        }

        binding.includeNavBar
    }

    override fun getLayoutId() = R.layout.discaver_activity
    override fun visibleProgress(isVisible: Boolean) {
        binding.progress.isVisible = isVisible
    }

    override fun navigationBarVisibility(isVisibility: Boolean) {
        binding.isNavBarVisible = isVisibility
    }

    private fun navigate(id: Int) {
        navController.navigate(id)
    }

}