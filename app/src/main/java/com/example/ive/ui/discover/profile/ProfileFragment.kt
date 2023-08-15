package com.example.ive.ui.discover.profile

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ive.R
import com.example.ive.component.model.DataNews
import com.example.ive.component.model.UserProfileViewData
import com.example.ive.databinding.FragmentProfileBinding
import com.example.ive.exstensions.showStatusBar
import com.example.ive.exstensions.toast
import com.example.ive.network.model.PhotoGallery
import com.example.ive.network.model.toDataNews
import com.example.ive.ui.adapter.PhotoUserGalleryAdapter
import com.example.ive.ui.base.BaseFragment
import com.example.ive.ui.discover.IProgressVisibility
import com.example.ive.ui.listeners.OnclickListeners
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private var dataNews: DataNews? = null

    private val args: ProfileFragmentArgs by navArgs()

    private val viewModel: ProfileViewModel by viewModels()

    private val iProgressBar: IProgressVisibility by lazy { activity as IProgressVisibility }
    // todo improve
    private val adapterPhoto = PhotoUserGalleryAdapter(object : OnclickListeners<PhotoGallery> {
        override fun onClick(item: PhotoGallery) {
            val data = item.toDataNews()
            navigate(R.id.photoFragment, bundleOf("data" to data))
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("ProfileFragment", "onCreate")
    }

    override fun initViews() {
        showStatusBar()
        iProgressBar.visibleProgress(true)
        with(binding) {
            rvGallery.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rvGallery.isNestedScrollingEnabled = false
            rvGallery.adapter = adapterPhoto
        }
        dataNews = try {
            args.dataNews
        } catch (e:Exception){
            toast("Me")
            null
        }
        getGalleries(dataNews?.user)
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

        viewModel.listPhoto.observe(viewLifecycleOwner){ it ->
            adapterPhoto.submitList(it?: emptyList())
            iProgressBar.visibleProgress(false)
            binding.swRefresh.isRefreshing = false
        }

    }
    override fun initListeners() {
        binding.swRefresh.setOnRefreshListener {
            adapterPhoto.removeList()
            getGalleries(dataNews?.user)
        }
    }
    private fun getGalleries(user: UserProfileViewData?) {
        if (user != null){
            initUserView(user)
            viewModel.getProfileInfo(user.tag?: "")
        } else {
            toast("Me")
            viewModel.getMyProfile()
        }
    }
    private fun initUserView(data: UserProfileViewData){
        binding.viewData = data
        binding.btFollowTo.text = resources.getString(R.string.follow, data.name)
    }
    override fun onStop() {
        super.onStop()
        adapterPhoto.removeList()
        viewModel.resetState()
    }
    override fun getDataBinding() = FragmentProfileBinding.inflate(layoutInflater)

}