package com.example.ive.ui.onboarding.start

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.ive.R
import com.example.ive.ui.base.BaseFragment
import com.example.ive.databinding.FragmentStartBinding
import com.example.ive.component.model.UserProfileViewData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : BaseFragment<FragmentStartBinding>() {

    override fun initListeners() {
        with(binding) {
            btnLogin.setOnClickListener { navigate(R.id.show_login) }
            btnReg.setOnClickListener { navigate(R.id.show_registration) }
        }
    }

    override fun getDataBinding() = FragmentStartBinding.inflate(layoutInflater)

}