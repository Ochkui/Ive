package com.example.ive.network.model

import android.os.Parcelable
import android.provider.ContactsContract.Data
import com.example.ive.component.model.DataNews
import com.example.ive.component.model.UserProfileViewData
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class PhotoData(
    val id: String,
    val urls: Urls,
    val user: User

) : Parcelable

@Parcelize
class User(
    @SerializedName("id") var id: String? = null,
    @SerializedName("username") var username: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("profile_image") var profileImage: ProfileImage? = ProfileImage(),
    @SerializedName("total_collections") var totalCollections: Int? = null,

    ) : Parcelable

@Parcelize
class ProfileImage(

    @SerializedName("small") var small: String? = null,
    @SerializedName("medium") var medium: String? = null,
    @SerializedName("large") var large: String? = null

) : Parcelable

@Parcelize
class Urls(
    @SerializedName("full") var full: String? = null,
    @SerializedName("regular"  ) var regular : String? = null,
    @SerializedName("small") var small: String? = null,

    ) : Parcelable


fun PhotoData.toDataNews() = DataNews(
    user = UserProfileViewData(user.profileImage?.small,user.name,user.username),
    imageUrls = urls.regular,
    photoId = id
)