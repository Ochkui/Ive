package com.example.ive.api

import com.example.ive.network.model.PhotoData
import retrofit2.Response
import retrofit2.http.GET

interface PhotoApi {

    @GET(GET_PHOTOS)
    suspend fun getPhoto(): Response<List<PhotoData>>

    companion object{
        const val GET_PHOTOS = "/photos"
    }
}