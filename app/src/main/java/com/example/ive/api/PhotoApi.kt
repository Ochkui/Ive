package com.example.ive.api

import androidx.annotation.IntRange
import com.example.ive.network.model.PhotoData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoApi {

    @GET(GET_PHOTOS)
    suspend fun getPhoto(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("per_page") @IntRange(from = 1, to = MAX_PAGE_SIZE.toLong())
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Response<List<PhotoData>>

    companion object {
        const val GET_PHOTOS = "/photos"
        const val DEFAULT_PAGE_SIZE = 10
        const val MAX_PAGE_SIZE = 10
    }
}