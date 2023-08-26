package com.example.ive.data

import androidx.room.TypeConverter
import com.example.ive.network.model.Urls
import com.example.ive.network.model.User
import com.google.gson.Gson

class UrlsTypeConverter {
    @TypeConverter
    fun fromUrls(urls: Urls): String {
        return Gson().toJson(urls)
    }

    @TypeConverter
    fun toUrls(json: String): Urls {
        return Gson().fromJson(json, Urls::class.java)
    }
}

class UserTypeConverter {
    @TypeConverter
    fun fromUser(user: User): String {
        return Gson().toJson(user)
    }

    @TypeConverter
    fun toUser(json: String): User {
        return Gson().fromJson(json, User::class.java)
    }
}


