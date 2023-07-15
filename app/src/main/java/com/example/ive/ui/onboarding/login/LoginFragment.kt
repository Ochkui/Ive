package com.example.ive.ui.onboarding.login

import android.content.Intent
import com.example.ive.OnboardingActivity
import com.example.ive.ui.base.BaseFragment
import com.example.ive.databinding.FragmentLoginBinding
import com.example.ive.ui.PhotoActivity
import com.example.ive.ui.discover.DiscoverActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override fun initListeners() {
        with(binding) {
            ibBack.setOnClickListener { onBack() }
            btnLogin.setOnClickListener {
                navigateTo(DiscoverActivity::class.java)
                requireActivity().finish()
            }
        }
    }

    override fun getDataBinding() = FragmentLoginBinding.inflate(layoutInflater)
}