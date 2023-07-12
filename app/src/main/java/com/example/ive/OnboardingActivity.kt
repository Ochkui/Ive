package com.example.ive

import androidx.core.view.isVisible
import com.example.ive.base.BaseActivity
import com.example.ive.databinding.StartActivityBinding
import com.example.ive.ui.discover.IProgressVisibility
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class OnboardingActivity : BaseActivity<StartActivityBinding>(), IProgressVisibility {


    override fun getLayoutId() = R.layout.start_activity
    override fun visibleProgress(isVisible: Boolean) {
        binding.progress.isVisible = isVisible
    }

}