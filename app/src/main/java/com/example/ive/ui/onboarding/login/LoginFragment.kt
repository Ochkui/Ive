package com.example.ive.ui.onboarding.login

import android.content.Intent
import com.example.ive.base.BaseFragment
import com.example.ive.databinding.FragmentLoginBinding
import com.example.ive.ui.discover.DiscoverActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override fun initListeners() {
        with(binding) {
            ibBack.setOnClickListener { onBack() }
            btnLogin.setOnClickListener {
                val intent = Intent(activity, DiscoverActivity::class.java)
                requireActivity().startActivity(intent)
            }
        }
    }

    override fun getDataBinding() = FragmentLoginBinding.inflate(layoutInflater)
}