package com.example.ive.ui.discover.photo

import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.example.ive.R
import com.example.ive.component.model.DataNews
import com.example.ive.databinding.FragmentPhotoBinding
import com.example.ive.exstensions.hideStatusBar
import com.example.ive.ui.base.BaseFragment
import com.example.ive.ui.discover.DiscoverActivity
import com.example.ive.ui.discover.INavigationBarVisibility
import com.squareup.picasso.Picasso

class PhotoFragment: BaseFragment<FragmentPhotoBinding>() {

    private val visibilityNavBar: INavigationBarVisibility by lazy { activity as INavigationBarVisibility }
    private var photo: DataNews? = null

    private val args: PhotoFragmentArgs by navArgs()

    override fun initViews() {
        hideStatusBar()
        visibilityNavBar.navigationBarVisibility(false)
        photo = args.data

        Picasso.get()
            .load(photo?.imageUrls)
            .into(binding.ibImage)

        with(binding.vUserProf) {
            setViewData(photo?.user!!)
            binding.vUserProf.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }
    }

    override fun initListeners() {
        binding.ibExit.setOnClickListener{
            goBack()
        }

        binding.vUserProf.setOnClickListener {
            visibilityNavBar.navigationBarVisibility(true)
            (activity as DiscoverActivity).navigateToMenu(R.id.userProfileFragment)
            navigate(
                PhotoFragmentDirections.showProfile(photo?.user)
            )
        }
    }

    override fun getDataBinding() = FragmentPhotoBinding.inflate(layoutInflater)
}