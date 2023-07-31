package com.example.ive.ui.discover

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.ive.R
import com.example.ive.databinding.DiscaverActivityBinding
import com.example.ive.ui.base.BaseActivity
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DiscoverActivity : BaseActivity<DiscaverActivityBinding>(), IProgressVisibility,INavigationBarVisibility,NavigationBarView.OnItemSelectedListener {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    private val sharedViewModel:DiscoverSharedViewModel by viewModels()
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
        navigationBarVisibility(true)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_container_discover) as NavHostFragment
        navController = navHostFragment.navController

        binding.btNav.setupWithNavController(navController)
        binding.btNav.setOnItemSelectedListener(this)
    }

    fun navigateToMenu(fragmentId:Int) {
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
    }

    override fun getLayoutId() = R.layout.discaver_activity

    override fun visibleProgress(isVisible: Boolean) {
        binding.progress.isVisible = isVisible
    }

    override fun navigationBarVisibility(isVisibility: Boolean) {
        binding.isNavBarVisible = isVisibility
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.userProfileFragment -> {
            //TODO set user name
                sharedViewModel.saveUserName("Stas")
                if (navController.currentDestination?.id == R.id.profileFragment){
                    navController.navigate(R.id.userProfileFragment)
                }

                item.onNavDestinationSelected(navController)
            }
            R.id.discoverFragment -> {
                navController.navigate(R.id.discoverFragment)
                item.onNavDestinationSelected(navController)
            }

            else -> {
                item.onNavDestinationSelected(navController)
            }
        }
        return true
    }
}