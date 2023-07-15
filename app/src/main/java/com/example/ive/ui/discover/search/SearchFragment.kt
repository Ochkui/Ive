package com.example.ive.ui.discover.search

import com.example.ive.ui.base.BaseFragment
import com.example.ive.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment: BaseFragment<FragmentSearchBinding>() {

    override fun getDataBinding() = FragmentSearchBinding.inflate(layoutInflater)
}