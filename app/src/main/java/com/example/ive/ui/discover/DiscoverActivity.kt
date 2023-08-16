package com.example.ive.ui.discover

import android.os.Bundle
import android.support.annotation.IdRes
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.ive.R
import com.example.ive.databinding.DiscaverActivityBinding
import com.example.ive.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DiscoverActivity : BaseActivity<DiscaverActivityBinding>(), IProgressVisibility,
    INavigationBarVisibility {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initListeners()
    }

    private fun initView() {
        navigationBarVisibility(true)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_container_discover) as NavHostFragment
        navController = navHostFragment.navController

        binding.btNav.setupWithNavController(navController)
    }

    fun navigateToMenu(@IdRes fragmentId: Int) {
        binding.btNav.selectedItemId(fragmentId)
    }

    private fun initListeners() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            when (destination.id) {
                R.id.photoFragment -> {
                    binding.btNav.visibility = View.GONE
                }

                else -> {
                    binding.btNav.visibility = View.VISIBLE
                }
            }
        }
        binding.btNav.setOnItemSelectedListener {
            when (it.itemId) {

                R.id.discoverFragment -> itemSelectNavigation(R.id.discoverFragment)

                R.id.searchFragment -> itemSelectNavigation(R.id.searchFragment)

                R.id.profileFragment -> itemSelectNavigation(R.id.profileFragment)

                R.id.chatFragment -> itemSelectNavigation(R.id.chatFragment)

                else -> {
                    false
                }
            }
        }
    }

    private fun itemSelectNavigation(fragment: Int) : Boolean {
        return if (navController.currentDestination?.id != fragment) {
            navController.navigate(fragment)
            true
        } else {
            false
        }
    }

    override fun getLayoutId() = R.layout.discaver_activity

    override fun visibleProgress(isVisible: Boolean) {
        binding.progress.isVisible = isVisible
    }

    override fun navigationBarVisibility(isVisibility: Boolean) {
        binding.isNavBarVisible = isVisibility
    }

}