package com.example.ive.ui.discover

import android.os.Bundle
import android.widget.ImageButton
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.ive.R
import com.example.ive.ui.base.BaseActivity
import com.example.ive.databinding.DiscaverActivityBinding
import com.example.ive.exstensions.toast
import com.example.ive.utils.animationScale
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverActivity : BaseActivity<DiscaverActivityBinding>(), IProgressVisibility {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var btHome: ImageButton
    private lateinit var btSearch: ImageButton
    private lateinit var btAdd: ImageButton
    private lateinit var btChat: ImageButton
    private lateinit var btProfile: ImageButton
    private lateinit var buttonFlags: MutableMap<Int, Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initListeners()
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

        buttonFlags = mutableMapOf(
            R.id.ib_home to true,
            R.id.ib_search to false,
            R.id.ib_add to false,
            R.id.ib_chats to false,
            R.id.ib_profile to false
        )
    }

    private fun initListeners() {

        btHome.setOnClickListener {
            animationScale(it, this@DiscoverActivity)
            if (!navigate(R.id.show_home, it.id)) toast("Home")
        }

        btSearch.setOnClickListener {
            animationScale(it, this@DiscoverActivity)
            if (!navigate(R.id.show_search, it.id)) toast("Search")
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

    private fun navigate(id: Int) {
        navController.navigate(id)
    }

    private fun navigate(showId: Int, resId: Int): Boolean {
        return if (buttonFlags[resId] == false) {
            resetButtonFlags(resId)
            navController.navigate(showId)
            true
        } else {
            false
        }
    }

    private fun resetButtonFlags(id: Int) {
        buttonFlags.replaceAll { _, _ -> false }
        buttonFlags[id] = true
    }

}