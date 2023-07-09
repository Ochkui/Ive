package com.example.ive.model

import androidx.annotation.DrawableRes

data class UserProfileViewData (
    @DrawableRes var image:Int,
    var name:String,
    var tag:String
)