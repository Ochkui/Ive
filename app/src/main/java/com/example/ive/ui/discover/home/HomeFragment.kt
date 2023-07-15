package com.example.ive.ui.discover.home

import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ive.base.BaseFragment
import com.example.ive.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()
    private val adapterNews = NewsAdapter()
    private val adapterPhoto = PhotoAdapter()

    override fun initObservers() {
        super.initObservers()
        viewModel.listPhoto.observe(viewLifecycleOwner, Observer {
            adapterNews.submitList(it)
            adapterPhoto.submitList(it)
        })
    }

    override fun initViews() {
        val stLayoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
//        stLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS

        with(binding) {

            rvListItem.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvListPhoto.isNestedScrollingEnabled = false
            rvListItem.adapter = adapterNews
            rvListPhoto.setHasFixedSize(false)
            rvListPhoto.layoutManager = stLayoutManager
            rvListPhoto.adapter = adapterPhoto
        }
    }

    override fun initListeners() {
        binding.consLayout.setOnClickListener { progressVisibility.visibleProgress(true) }
    }

    override fun getDataBinding() = FragmentHomeBinding.inflate(layoutInflater)
}