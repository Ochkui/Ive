package com.example.ive.ui.discover.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ive.databinding.FragmentHomeBinding
import com.example.ive.exstensions.showStatusBar
import com.example.ive.exstensions.toast
import com.example.ive.ui.adapter.NewsAdapter
import com.example.ive.ui.adapter.PhotoAdapter
import com.example.ive.ui.base.BaseFragment
import com.example.ive.ui.discover.IProgressVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    private val adapterPhoto = PhotoAdapter(
        listener = { item -> navigate(HomeFragmentDirections.homeToPhoto(item))}
    )

    private val adapterNews = NewsAdapter(
        userProfileListeners = { item -> navigate(HomeFragmentDirections.homeToProfile(item))},
        imageListener = { item -> navigate(HomeFragmentDirections.homeToPhoto(item)) }
    )

    private val iProgressBar: IProgressVisibility by lazy { activity as IProgressVisibility }

    override fun initObservers() {

        viewModel.isConnected.observe(viewLifecycleOwner){
            checkNetWork(it)
        }

        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {

                is HomeViewModel.UiState.Error -> {
                    toast(it.message)
                    iProgressBar.visibleProgress(false)
                }

                is HomeViewModel.UiState.None -> {
                    iProgressBar.visibleProgress(false)
                }

                is HomeViewModel.UiState.Loading -> {
                    iProgressBar.visibleProgress(true)
                }
            }
        }

        viewModel.popular.observe(viewLifecycleOwner) {
            adapterPhoto.submitList(it)
            binding.swRefresh.isRefreshing = false
            iProgressBar.visibleProgress(false)
        }
        getDataNews()
    }

    override fun initViews() {
        val isConnected = viewModel.isConnected.value ?: false
        checkNetWork(isConnected)
        showStatusBar()
        iProgressBar.visibleProgress(true)
        val stLayoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL
        )

        with(binding) {
            rvListItem.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(), LinearLayoutManager.HORIZONTAL, false
                )
                adapter = adapterNews
            }

            rvListPhoto.apply {
                isNestedScrollingEnabled = false
                setHasFixedSize(false)
                layoutManager = stLayoutManager
                adapter = adapterPhoto
                getDataNews()
            }
        }
    }

    private fun checkNetWork(isConnected: Boolean){
        if (!isConnected) {
            binding.btSeeMore.visibility = View.GONE
            binding.swRefresh.isEnabled = false
            binding.swRefresh.isRefreshing = true
            toast("No connection!")
        } else {
            binding.btSeeMore.visibility = View.VISIBLE
            binding.swRefresh.isEnabled = true
            binding.swRefresh.isRefreshing = false
            toast("Connection!")
        }
    }

    override fun initListeners() {
        binding.swRefresh.setOnRefreshListener {
            getDataNews()
            adapterPhoto.removeList()
            viewModel.refresh()
        }

        binding.btSeeMore.setOnClickListener {
            viewModel.getPhotoPopular()
        }
    }

    private fun getDataNews(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest { pagingData ->
                adapterNews.submitData(PagingData.empty())
                adapterNews.submitData(pagingData)
            }
        }
    }

    override fun getDataBinding() = FragmentHomeBinding.inflate(layoutInflater)

    override fun onStop() {
        super.onStop()
        // todo improve SingleLiveEvent
        viewModel.resetState()
    }

}