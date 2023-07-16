package com.example.ive.component.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataNews(
    val user: UserProfileViewData,
    val imageUrls: String?,
    val photoId:String,
    val location:String?
): Parcelable
