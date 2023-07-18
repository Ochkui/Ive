package com.example.ive.network.model

import android.os.Parcelable
import com.example.ive.component.model.DataNews
import com.example.ive.component.model.UserProfileViewData
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class PhotoData(
    val id: String,
    val urls: Urls,
    val user: User,
    val location: String?

) : Parcelable

@Parcelize
class PhotoDataList(
    @SerializedName("results") var list:MutableList<PhotoData>
) : Parcelable

@Parcelize
class PhotoGallery(
    val id: String,
    val cover_photo: CoverPhoto,
    val user: User

) : Parcelable


@Parcelize
class CoverPhoto(

    @SerializedName("id") var id: String? = null,
    @SerializedName("urls") var urls: Urls? = Urls(),
    @SerializedName("user") var user: User? = User()

):Parcelable

@Parcelize
class User(
    @SerializedName("id") var id: String? = null,
    @SerializedName("username") var username: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("profile_image") var profileImage: ProfileImage? = ProfileImage(),
    @SerializedName("total_collections") var totalCollections: Int? = null,
    @SerializedName("location") var location: String? = null

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
    @SerializedName("regular") var regular: String? = null,
    @SerializedName("small") var small: String? = null,

    ) : Parcelable


fun PhotoData.toDataNews() = DataNews(
    user = UserProfileViewData(user.profileImage?.large, user.name, user.username, user.location),
    imageUrls = urls.regular,
    photoId = id,
    location = location
)

//fun PhotoDataList.toDataNews() = DataNews(
//    user = UserProfileViewData(list.user.profileImage?.large, list.user.name, list.user.username, list.user.location),
//    imageUrls = list.urls.regular,
//    photoId = list.id,
//    location = list.location
//)

fun PhotoGallery.toDataNews() = DataNews(
    user = UserProfileViewData(user.profileImage?.large,user.name,user.username,user.location),
    imageUrls = cover_photo.urls?.regular,
    photoId = id,
    location = user.location
)