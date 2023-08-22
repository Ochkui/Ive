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
            if (!it){
                toast("No Connection!")
            }
            checkNetWork(it)
        }

        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {

                is HomeViewModel.UiState.Error -> {
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

            if (viewModel.networkChecker.isInternetAvailable()){
                binding.swRefresh.isRefreshing = false
            }
            iProgressBar.visibleProgress(false)
        }

        viewModel.latest.observe(viewLifecycleOwner){
            viewLifecycleOwner.lifecycleScope.launch{
                adapterNews.submitData(PagingData.from(it))
            }
        }

    }

    override fun initViews() {
        showStatusBar()
        checkNetWork(viewModel.networkChecker.isInternetAvailable())
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
                getDataNews()
            }

            rvListPhoto.apply {
                isNestedScrollingEnabled = false
                setHasFixedSize(false)
                layoutManager = stLayoutManager
                adapter = adapterPhoto
            }
        }
    }

    private fun getDataNews(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest { pagingData ->
                adapterNews.submitData(pagingData)
            }
        }
    }

    private fun checkNetWork(isConnected: Boolean){
        if (!isConnected) {
            binding.btSeeMore.visibility = View.GONE
            binding.swRefresh.isEnabled = false
            binding.swRefresh.isRefreshing = true
        } else {
            binding.btSeeMore.visibility = View.VISIBLE
            binding.swRefresh.isEnabled = true
            binding.swRefresh.isRefreshing = false
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

    override fun getDataBinding() = FragmentHomeBinding.inflate(layoutInflater)

    override fun onStop() {
        super.onStop()
        // todo improve SingleLiveEvent
        viewModel.resetState()
    }

}