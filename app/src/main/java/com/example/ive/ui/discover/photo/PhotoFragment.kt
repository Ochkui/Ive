package com.example.ive.ui.discover.photo

import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.example.ive.R
import com.example.ive.component.model.DataNews
import com.example.ive.databinding.FragmentPhotoBinding
import com.example.ive.exstensions.getParcel
import com.example.ive.ui.base.BaseFragment
import com.example.ive.ui.discover.INavigationBarVisibility
import com.squareup.picasso.Picasso

class PhotoFragment: BaseFragment<FragmentPhotoBinding>() {

    private val visibilityNavBar: INavigationBarVisibility by lazy { activity as INavigationBarVisibility }
    private var photo: DataNews? = null

    override fun initViews() {
        visibilityNavBar.navigationBarVisibility(false)
        photo = getParcel("data")

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
            navigate(R.id.profileFragment, bundleOf("data" to photo?.user))
        }
    }

    override fun getDataBinding() = FragmentPhotoBinding.inflate(layoutInflater)
}