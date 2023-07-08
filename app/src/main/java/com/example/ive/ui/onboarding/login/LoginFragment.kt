package com.example.ive.ui.onboarding.login

import android.os.Bundle
import android.view.View
import com.example.ive.base.BaseFragment
import com.example.ive.databinding.FragmentLoginBinding

class LoginFragment: BaseFragment<FragmentLoginBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        with(binding) {
            ibBack.setOnClickListener { v -> onBack(v) }
            btnLogin.setOnClickListener { showToast("To be continued") }
        }
    }

    override fun getDataBinding() = FragmentLoginBinding.inflate(layoutInflater)
}