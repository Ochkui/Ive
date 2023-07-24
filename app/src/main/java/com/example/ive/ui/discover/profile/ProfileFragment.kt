package com.example.ive.ui.discover.profile

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ive.R
import com.example.ive.component.model.UserProfileViewData
import com.example.ive.databinding.FragmentProfileBinding
import com.example.ive.exstensions.getParcel
import com.example.ive.exstensions.toast
import com.example.ive.network.model.PhotoGallery
import com.example.ive.network.model.toDataNews
import com.example.ive.ui.adapter.PhotoUserGalleryAdapter
import com.example.ive.ui.base.BaseFragment
import com.example.ive.ui.discover.INavigationBarVisibility
import com.example.ive.ui.discover.IProgressVisibility
import com.example.ive.ui.listeners.OnclickListeners
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private lateinit var user: UserProfileViewData
    private val viewModel: ProfileViewModel by viewModels()
    private val iProgressBar: IProgressVisibility by lazy { activity as IProgressVisibility }
    private val visibilityNavBar: INavigationBarVisibility by lazy { activity as INavigationBarVisibility }


    private val adapterPhoto = PhotoUserGalleryAdapter(object : OnclickListeners<PhotoGallery> {
        override fun onClick(item: PhotoGallery, number: Int) {
            val data = item.toDataNews()
            navigate(R.id.photoFragment, bundleOf("data" to data))
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getParcel<UserProfileViewData>(KEY_DATA)?.let {
            user = it
        } ?: run {
            goBack()
            toast("Not found")
        }
    }

    override fun initObservers() {
        viewModel.listPhoto.observe(viewLifecycleOwner) {
            if (it != null) {
                adapterPhoto.submitList(it)
                iProgressBar.visibleProgress(false)
                binding.swRefresh.isRefreshing = false
                binding.btSeeMore.visibility = View.VISIBLE
            }
        }
        viewModel.listError.observe(viewLifecycleOwner) {
            val text = (it)
        }
    }

    override fun initViews() {
        binding.btSeeMore.visibility = View.INVISIBLE
        iProgressBar.visibleProgress(true)
        user.tag?.let {
            viewModel.getGalleries(it)
            binding.swRefresh.setOnRefreshListener {
                viewModel.getGalleries(it)
            }
        } ?: run {
            goBack()
            toast("Not found")
        }

        with(binding) {

            btFollowTo.text = resources.getString(R.string.follow, user.name)
            viewData = user
            rvGallery.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rvGallery.isNestedScrollingEnabled = false
            rvGallery.adapter = adapterPhoto
        }
    }

    override fun getDataBinding() = FragmentProfileBinding.inflate(layoutInflater)

    companion object {
        const val KEY_DATA = "data"
    }
}