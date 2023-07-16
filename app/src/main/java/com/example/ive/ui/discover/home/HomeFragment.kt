package com.example.ive.ui.discover.home

import android.content.Context
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ive.R
import com.example.ive.component.model.DataNews
import com.example.ive.ui.base.BaseFragment
import com.example.ive.databinding.FragmentHomeBinding
import com.example.ive.exstensions.toast
import com.example.ive.ui.PhotoActivity
import com.example.ive.ui.adapter.NewsAdapter
import com.example.ive.ui.adapter.PhotoAdapter
import com.example.ive.ui.discover.IProgressVisibility
import com.example.ive.ui.discover.OnSwipeRefreshListener
import com.example.ive.ui.listeners.OnclickListeners
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    private val adapterPhoto = PhotoAdapter(object : OnclickListeners<DataNews>{
        override fun onClick(item: DataNews, number: Int) {
            navigateTo(PhotoActivity::class.java,bundleOf ("data" to item))
        }

    })

    private val adapterNews = NewsAdapter(object : OnclickListeners<DataNews>{
        override fun onClick(item: DataNews, number: Int) {

            if (number == 1){
                navigateTo(PhotoActivity::class.java,bundleOf ("data" to item))
            } else {
                navigate(R.id.show_profile, bundleOf ("data" to item.user))
            }
            toast("Click")
        }
    })

    private val iProgressBar: IProgressVisibility by lazy { activity as IProgressVisibility }

    override fun initObservers() {
        viewModel.listPhoto.observe(viewLifecycleOwner, Observer {
            adapterNews.submitList(it)
            adapterPhoto.submitList(it)
            iProgressBar.visibleProgress(false)
            binding.swRefresh.isRefreshing = false
        })
    }

    override fun initViews() {

        iProgressBar.visibleProgress(true)
        val stLayoutManager = StaggeredGridLayoutManager(
            2,StaggeredGridLayoutManager.VERTICAL)

        with(binding) {
            rvListItem.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvListPhoto.isNestedScrollingEnabled = false
            rvListItem.adapter = adapterNews
            rvListPhoto.setHasFixedSize(false)
            rvListPhoto.layoutManager = stLayoutManager
            rvListPhoto.adapter = adapterPhoto
        }
    }

    override fun initListeners() {
        binding.swRefresh.setOnRefreshListener {
            viewModel.getPhoto()
        }
//        binding.consLayout.setOnClickListener { progressVisibility.visibleProgress(true) }
    }

    override fun getDataBinding() = FragmentHomeBinding.inflate(layoutInflater)

}