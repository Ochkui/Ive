package com.example.ive.ui.discover.discover

import com.example.ive.base.BaseFragment
import com.example.ive.databinding.FragmentDiscoverBinding

class DiscoverFragment : BaseFragment<FragmentDiscoverBinding>() {

    override fun initListeners() {
        binding.consLayout.setOnClickListener { progressVisibility.visibleProgress(true) }
    }

    override fun getDataBinding() = FragmentDiscoverBinding.inflate(layoutInflater)
}