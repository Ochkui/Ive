package com.example.ive.repository

import android.view.ViewDebug.IntToString
import com.example.ive.api.PhotoApi
import com.example.ive.network.ApiResponse
import com.example.ive.network.BaseRepository
import com.example.ive.network.model.PhotoData
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val api:PhotoApi
):BaseRepository(){

    suspend fun getPhotos(): ApiResponse<List<PhotoData>> = request{ api.getPhoto() }
}