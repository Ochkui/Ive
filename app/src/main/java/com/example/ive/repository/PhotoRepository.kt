package com.example.ive.repository

import android.view.ViewDebug.IntToString
import com.example.ive.api.PhotoApi
import com.example.ive.network.ApiResponse
import com.example.ive.network.BaseRepository
import com.example.ive.network.model.PhotoData
import com.example.ive.network.model.PhotoGallery
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val api:PhotoApi
):BaseRepository(){

    suspend fun getPhotos(): ApiResponse<List<PhotoData>> {
        return request { api.getPhoto() }
    }

    suspend fun getGalleries(username: String):ApiResponse<List<PhotoGallery>>{
        return request { api.getGallery(username) }
    }

}