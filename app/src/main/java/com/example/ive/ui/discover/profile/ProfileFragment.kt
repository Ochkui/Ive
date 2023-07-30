package com.example.ive.ui.discover.profile

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ive.R
import com.example.ive.component.model.UserProfileViewData
import com.example.ive.databinding.FragmentProfileBinding
import com.example.ive.exstensions.toast
import com.example.ive.network.model.PhotoGallery
import com.example.ive.network.model.toDataNews
import com.example.ive.ui.adapter.PhotoUserGalleryAdapter
import com.example.ive.ui.base.BaseFragment
import com.example.ive.ui.discover.DiscoverSharedViewModel
import com.example.ive.ui.discover.INavigationBarVisibility
import com.example.ive.ui.discover.IProgressVisibility
import com.example.ive.ui.listeners.OnclickListeners
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private lateinit var user: UserProfileViewData
    private val viewModel: ProfileViewModel by viewModels()
    private val sharedViewModel:DiscoverSharedViewModel by activityViewModels()
    private val args: ProfileFragmentArgs by navArgs()
    private val iProgressBar: IProgressVisibility by lazy { activity as IProgressVisibility }
    private val visibilityNavBar: INavigationBarVisibility by lazy { activity as INavigationBarVisibility }

    private lateinit var tag: String


    private val adapterPhoto = PhotoUserGalleryAdapter(object : OnclickListeners<PhotoGallery> {
        override fun onClick(item: PhotoGallery, number: Int) {
            val data = item.toDataNews()
            navigate(R.id.photoFragment, bundleOf("data" to data))
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        args.data?.let {
            user = it
            tag = user.tag?: ""
        } ?: run {
            toast("Not found")
            onDestroy()
        }

    }

    override fun initObservers() {
        viewModel.uiState.observe(viewLifecycleOwner){
            when(it){
                is ProfileViewModel.UiState.Error -> {
                    toast(it.message)
                }

                ProfileViewModel.UiState.Loading -> {
                    iProgressBar.visibleProgress(true)
                }

                ProfileViewModel.UiState.None -> {
                    iProgressBar.visibleProgress(false)
                }
            }
        }

        viewModel.listPhoto.observe(viewLifecycleOwner){
            adapterPhoto.submitList(it?: emptyList())
            iProgressBar.visibleProgress(false)
            binding.swRefresh.isRefreshing = false
        }

    }

    override fun initViews() {
        iProgressBar.visibleProgress(true)
        with(binding) {

            btFollowTo.text = resources.getString(R.string.follow, user.name)
            viewData = user
            rvGallery.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rvGallery.isNestedScrollingEnabled = false
            rvGallery.adapter = adapterPhoto
        }

        viewModel.getGalleries(tag)

    }
    override fun initListeners() {
        binding.swRefresh.setOnRefreshListener {
            adapterPhoto.removeList()
            viewModel.getGalleries(tag)
        }
    }
    override fun onStop() {
        super.onStop()
        adapterPhoto.removeList()
        viewModel.resetState()
    }

    override fun getDataBinding() = FragmentProfileBinding.inflate(layoutInflater)

    companion object {
        const val KEY_DATA = "data"
    }
}