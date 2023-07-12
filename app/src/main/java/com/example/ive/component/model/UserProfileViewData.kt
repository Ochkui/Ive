package com.example.ive.component.model

import androidx.annotation.DrawableRes

data class UserProfileViewData (
    @DrawableRes var image:Int,
    var name:String,
    var tag:String
)