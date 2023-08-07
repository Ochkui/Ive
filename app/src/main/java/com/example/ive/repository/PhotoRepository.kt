package com.example.ive.repository

import com.example.ive.api.PhotoApi
import com.example.ive.network.ApiResponse
import com.example.ive.network.BaseRepository
import com.example.ive.network.model.PhotoData
import com.example.ive.network.model.PhotoDataList
import com.example.ive.network.model.PhotoGallery
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val api:PhotoApi
):BaseRepository(){

    suspend fun getPhotos(orderBy:String = "latest",page: Int = 1, pageSize:Int = 10): ApiResponse<List<PhotoData>> {
        return request { api.getPhoto(orderBy, page = page, pageSize = pageSize) }
    }

    suspend fun getGalleries(username: String):ApiResponse<List<PhotoGallery>>{
        return request { api.getGallery(username) }
    }

    suspend fun getSearchPhoto(query:String,countItem:Int,page:Int): ApiResponse<PhotoDataList> {
        return request { api.getSearchPhoto(query,countItem,page) }
    }



}