package com.example.ive.component

import android.content.Context
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.ive.component.model.UserProfileViewData
import com.example.ive.databinding.ViewUserProfileBinding
import com.squareup.picasso.Picasso

class UserProfileView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    desStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleAttr, desStyleRes) {

    private var binding: ViewUserProfileBinding =
        ViewUserProfileBinding.inflate(LayoutInflater.from(context), this, true)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    fun setViewData(userProfile: UserProfileViewData) {
        binding.viewData = userProfile
    }

    fun setTextColor(@ColorInt color: Int){
        with(binding) {
            tvName.setTextColor(color)
            tvTag.setTextColor(color)
        }
    }
}