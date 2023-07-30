package com.example.ive.ui.discover.profile

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.example.ive.databinding.FragmentUserProfileBinding
import com.example.ive.exstensions.toast
import com.example.ive.ui.base.BaseFragment
import com.example.ive.ui.discover.DiscoverSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : BaseFragment<FragmentUserProfileBinding>() {

    private lateinit var userName:String

    private val sharedViewModel:DiscoverSharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun getDataBinding() = FragmentUserProfileBinding.inflate(layoutInflater)

    override fun initViews() {

    }

    override fun initObservers() {
        sharedViewModel.userName.observe(viewLifecycleOwner){
            toast(it)
        }
    }

}