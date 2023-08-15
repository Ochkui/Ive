package com.example.ive.component.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserProfileViewData (
    var image:String?,
    var name:String?,
    var tag:String?,
    var location:String?
): Parcelable