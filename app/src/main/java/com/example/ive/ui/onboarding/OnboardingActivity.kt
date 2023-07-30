package com.example.ive.ui.onboarding

import androidx.core.view.isVisible
import com.example.ive.R
import com.example.ive.ui.base.BaseActivity
import com.example.ive.databinding.StartActivityBinding
import com.example.ive.ui.discover.IProgressVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : BaseActivity<StartActivityBinding>(), IProgressVisibility {


    override fun getLayoutId() = R.layout.start_activity
    override fun visibleProgress(isVisible: Boolean) {
        binding.progress.isVisible = isVisible
    }

}