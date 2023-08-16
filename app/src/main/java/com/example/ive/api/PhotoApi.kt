package com.example.ive.api

import androidx.annotation.IntRange
import com.example.ive.network.model.PhotoData
import com.example.ive.network.model.PhotoDataList
import com.example.ive.network.model.PhotoGallery
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotoApi {

    @GET(GET_PHOTOS)
    suspend fun getPhoto(
        @Query(ORDER_BY) orderBy: String,
        @Query(PAGE) page: Int = 1,
        @Query(COUNT_ITEM) @IntRange(from = 1, to = MAX_PAGE_SIZE.toLong())
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Response<List<PhotoData>>

    @GET(GET_GALLERY)
    suspend fun getGallery(@Path("username") username: String): Response<List<PhotoGallery>>

    @GET(SEARCH_PHOTO)
    suspend fun getSearchPhoto(
        @Query(QUERY) query: String,
        @Query(COUNT_ITEM) perPage: Int = 30,
        @Query(PAGE) page: Int = 1
    ): Response<PhotoDataList>

    companion object {
        const val ORDER_BY = "order_by"
        const val SEARCH_PHOTO = "search/photos"
        const val GET_GALLERY = "users/{username}/collections"
        const val PAGE = "page"
        const val QUERY = "query"
        const val COUNT_ITEM = "per_page"
        const val GET_PHOTOS = "/photos"
        const val DEFAULT_PAGE_SIZE = 10
        const val MAX_PAGE_SIZE = 10
    }
}