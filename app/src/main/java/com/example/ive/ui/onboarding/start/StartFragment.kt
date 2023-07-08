package com.example.ive.ui.onboarding.start
import android.os.Bundle
import android.view.View
import com.example.ive.R
import com.example.ive.base.BaseFragment
import com.example.ive.databinding.FragmentStartBinding

class StartFragment: BaseFragment<FragmentStartBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        with(binding) {
            btnLogin.setOnClickListener { navigate(R.id.show_login) }
            btnReg.setOnClickListener {navigate(R.id.show_registration)}
        }
    }

    override fun getDataBinding() = FragmentStartBinding.inflate(layoutInflater)

}