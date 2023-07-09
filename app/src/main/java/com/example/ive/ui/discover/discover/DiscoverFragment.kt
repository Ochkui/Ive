package com.example.ive.ui.discover.discover

import android.os.Bundle
import android.view.View
import com.example.ive.R
import com.example.ive.base.BaseFragment
import com.example.ive.databinding.FragmentDiscoverBinding

class DiscoverFragment : BaseFragment<FragmentDiscoverBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.consLayout.setOnClickListener {
            binding.consLayout.setBackgroundColor(requireActivity().getColor(R.color.white))
        }
    }

    override fun getDataBinding() = FragmentDiscoverBinding.inflate(layoutInflater)
}