package com.example.ive.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.children
import androidx.core.view.forEachIndexed
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.example.ive.databinding.ViewBottomNavBinding
import com.example.ive.exstensions.screenWidth
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.navigation.NavigationBarView

class BottomNavView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewBottomNavBinding =
        ViewBottomNavBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        setupNavView()
    }

    fun setupWithNavController(navController: NavController) {
        binding.navView.setupWithNavController(navController)
    }
    fun setOnItemSelectedListener(listener: NavigationBarView.OnItemSelectedListener){
        binding.navView.setOnItemSelectedListener(listener)
    }

    fun selectedItemId(fragmentId:Int){
        binding.navView.selectedItemId = fragmentId
    }

    private fun setupNavView() = with(binding) {
        navView.itemIconTintList = null

        val menuView = navView.getChildAt(0) as BottomNavigationMenuView
        val iconMargins = getIconMargins()

        menuView.forEachIndexed { index, view ->
            val iconView: View =
                view.findViewById(com.google.android.material.R.id.navigation_bar_item_icon_view)
            val iconContainer: View =
                view.findViewById(com.google.android.material.R.id.navigation_bar_item_icon_container)

            iconView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            iconContainer.setPadding(iconMargins[index].first, 0, iconMargins[index].second, 0)
        }
    }

    private fun getIconMargins(): List<Pair<Int, Int>> {
        val iconWidthList =
            binding.navView.menu.children.map { it.icon?.intrinsicWidth ?: 0 }.toList()
        val containerWidth = context.screenWidth / iconWidthList.size
        val bigIconMargin = (containerWidth - iconWidthList[CENTRAL_ICON_INDEX]) / 2
        val iconMargin = (context.screenWidth - iconWidthList.sum()) / iconWidthList.size

        val iconMargins = mutableListOf(
            bigIconMargin to bigIconMargin
        )

        for (i in 1 downTo 0) {
            val leftMargin = iconMargin - iconMargins[0].first
            val rightMargin = containerWidth - iconWidthList[i] - leftMargin

            iconMargins.add(leftMargin to rightMargin)
            iconMargins.add(0, rightMargin to leftMargin)
        }
        return iconMargins
    }

    private companion object {
        const val CENTRAL_ICON_INDEX = 2
    }
}