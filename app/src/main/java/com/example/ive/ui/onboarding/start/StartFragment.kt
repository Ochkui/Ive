package com.example.ive.ui.onboarding.start
import android.os.Bundle
import android.view.View
import com.example.ive.R
import com.example.ive.base.BaseFragment
import com.example.ive.databinding.FragmentStartBinding
import com.example.ive.model.UserProfileViewData

class StartFragment: BaseFragment<FragmentStartBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        initView()
    }

    private fun initView() {
        binding.vUserProf.setViewData(UserProfileViewData(R.drawable.profile_dev,"Mails","developer"))
    }

    private fun setupListeners() {
        with(binding) {
            btnLogin.setOnClickListener { navigate(R.id.show_login) }
            btnReg.setOnClickListener {navigate(R.id.show_registration)}
        }
    }

    override fun getDataBinding() = FragmentStartBinding.inflate(layoutInflater)

}