package com.example.ive.component

import android.content.Context
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.ive.component.model.UserProfileViewData
import com.example.ive.databinding.ViewUserProfileBinding

class UserProfileView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    desStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, desStyleRes) {

    private var binding: ViewUserProfileBinding =
        ViewUserProfileBinding.inflate(LayoutInflater.from(context), this, true)

    fun setViewData(userProfile: UserProfileViewData) {
        binding.viewData = userProfile
    }

    fun setTextColor(@ColorInt color: Int) {
        with(binding) {
            tvName.setTextColor(color)
            tvTag.setTextColor(color)
        }
    }
}