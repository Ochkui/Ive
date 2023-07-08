package com.example.ive.ui.onboarding.registration

import android.os.Bundle
import android.view.View
import com.example.ive.base.BaseFragment
import com.example.ive.databinding.FragmentRegistrationBinding

class RegistrationFragment:BaseFragment<FragmentRegistrationBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        with(binding) {
            ibBack.setOnClickListener { v -> onBack(v) }
            btnNext.setOnClickListener { showToast("To be continued") }
        }
    }


    override fun getDataBinding() = FragmentRegistrationBinding.inflate(layoutInflater)
}