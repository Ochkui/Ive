package com.example.ive.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.ive.databinding.ViewUserProfileBinding
import com.example.ive.component.model.UserProfileViewData

class UserProfileView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    desStyleRes: Int
) : ConstraintLayout (context, attrs, defStyleAttr, desStyleRes) {

    private var binding:ViewUserProfileBinding
    constructor(context: Context, attrs: AttributeSet?,defStyleAttr: Int): this(context,attrs,defStyleAttr,0)
    constructor(context: Context, attrs: AttributeSet?): this(context,attrs,0)
    constructor(context: Context): this(context,null)


    init {
        binding = ViewUserProfileBinding.inflate(LayoutInflater.from(context),this,true)
    }

    fun setViewData(userProfile: UserProfileViewData){
        binding.viewData = userProfile
    }
}