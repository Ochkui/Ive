package com.example.ive.ui.discover.home

import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ive.R
import com.example.ive.component.model.DataNews
import com.example.ive.databinding.FragmentHomeBinding
import com.example.ive.exstensions.toast
import com.example.ive.ui.adapter.NewsAdapter
import com.example.ive.ui.adapter.PhotoAdapter
import com.example.ive.ui.base.BaseFragment
import com.example.ive.ui.discover.DiscoverActivity
import com.example.ive.ui.discover.IProgressVisibility
import com.example.ive.ui.discover.photo.PhotoFragmentDirections
import com.example.ive.ui.listeners.OnclickListeners
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    private val adapterPhoto = PhotoAdapter(object : OnclickListeners<DataNews> {
        override fun onClick(item: DataNews) {
            navigate(
                HomeFragmentDirections.showPhoto(item)
            )
        }

    })

    private val adapterNews = NewsAdapter(object : OnclickListeners<DataNews> {
        override fun onClick(item: DataNews) {

            (activity as DiscoverActivity).navigateToMenu(R.id.userProfileFragment)
            navigate(
                HomeFragmentDirections.showProfile(item.user)
            )
        }
    }, object : OnclickListeners<DataNews>{
        override fun onClick(item: DataNews) {
            navigate(
                HomeFragmentDirections.showPhoto(item)
            )
        }

    })

    private val iProgressBar: IProgressVisibility by lazy { activity as IProgressVisibility }

    override fun initObservers() {

        viewModel.uiState.observe(viewLifecycleOwner){
            when(it){

                is HomeViewModel.UiState.Error ->{
                    toast(it.message)
                    iProgressBar.visibleProgress(false)
                }
                is HomeViewModel.UiState.None ->{
                    iProgressBar.visibleProgress(false)
                }
                is HomeViewModel.UiState.Loading -> {
                    iProgressBar.visibleProgress(true)
                }
            }
        }

        viewModel.news.observe(viewLifecycleOwner) {
            Log.i("HomeViewModel", "news send to adapter")
            adapterNews.submitList(it)
            binding.swRefresh.isRefreshing = false
            iProgressBar.visibleProgress(false)
        }

        viewModel.popular.observe(viewLifecycleOwner) {
            Log.i("HomeViewModel", "photo send to adapter")
            adapterPhoto.submitList(it)
            binding.swRefresh.isRefreshing = false
            iProgressBar.visibleProgress(false)
        }
    }

    override fun initViews() {

        iProgressBar.visibleProgress(true)
        val stLayoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL
        )

        with(binding) {
            rvListItem.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
            )
            rvListPhoto.isNestedScrollingEnabled = false
            rvListItem.adapter = adapterNews
            rvListPhoto.setHasFixedSize(false)
            rvListPhoto.layoutManager = stLayoutManager
            rvListPhoto.adapter = adapterPhoto
        }
    }

    override fun initListeners() {
        binding.swRefresh.setOnRefreshListener {
            adapterNews.removeList()
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
        viewModel.resetState()
    }

}