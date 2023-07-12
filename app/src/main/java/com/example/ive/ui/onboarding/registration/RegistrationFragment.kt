package com.example.ive.ui.onboarding.registration

import com.example.ive.base.BaseFragment
import com.example.ive.databinding.FragmentRegistrationBinding
import com.example.ive.exstensions.toast

class RegistrationFragment:BaseFragment<FragmentRegistrationBinding>() {

    override fun initListeners() {
        with(binding) {
            ibBack.setOnClickListener { v -> onBack(v) }
            btnNext.setOnClickListener { toast("To be continued") }
        }
    }

    override fun getDataBinding() = FragmentRegistrationBinding.inflate(layoutInflater)
}