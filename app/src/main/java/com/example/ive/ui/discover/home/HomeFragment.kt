package com.example.ive.ui.discover.home

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ive.component.model.DataNews
import com.example.ive.databinding.FragmentHomeBinding
import com.example.ive.exstensions.showStatusBar
import com.example.ive.exstensions.toast
import com.example.ive.ui.adapter.NewsAdapter
import com.example.ive.ui.adapter.PhotoAdapter
import com.example.ive.ui.base.BaseFragment
import com.example.ive.ui.discover.IProgressVisibility
import com.example.ive.ui.listeners.OnclickListeners
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    private val adapterPhoto = PhotoAdapter(object : OnclickListeners<DataNews> {
        override fun onClick(item: DataNews) {
            navigate(
                HomeFragmentDirections.homeToPhoto(item)
            )
        }

    })

    // todo improve
    private val adapterNews = NewsAdapter(
        userProfileListeners = ::navigateToProfile,
        imageListener = { item -> navigate(HomeFragmentDirections.homeToPhoto(item)) }
    )

    private fun navigateToProfile(item: DataNews) {
        navigate(HomeFragmentDirections.homeToProfile(item))
    }

    private val iProgressBar: IProgressVisibility by lazy { activity as IProgressVisibility }

    override fun initObservers() {
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

//        viewModel.news.observe(viewLifecycleOwner) {
//            Log.i("HomeViewModel", "news send to adapter")
////            adapterNews.submitList(it)
//            binding.swRefresh.isRefreshing = false
//            iProgressBar.visibleProgress(false)
//        }

        viewModel.popular.observe(viewLifecycleOwner) {
            Log.i("HomeViewModel", "photo send to adapter")
            adapterPhoto.submitList(it)
            binding.swRefresh.isRefreshing = false
            iProgressBar.visibleProgress(false)
        }
    }

    override fun initViews() {

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
            }
        }

        // todo improve
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest { pagingData ->
                adapterNews.submitData(pagingData)
            }
        }
    }

    override fun initListeners() {
        binding.swRefresh.setOnRefreshListener {
//            adapterNews.removeList()
            adapterNews.refresh()
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
        adapterNews.refresh()
    }

}