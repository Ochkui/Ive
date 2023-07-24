package com.example.ive.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ive.api.PhotoApi
import com.example.ive.component.model.DataNews
import com.example.ive.network.model.PhotoData
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

//class PhotoNewsPagingSource @Inject constructor(
//    private val photoApi: PhotoApi
//    ): PagingSource<Int,DataNews>(){
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataNews> {
//
//        return try {
//            val currentPage = params.key ?: 1
//            val response = photoApi.getPhoto(page = )
//            val data = response.body()!!
//            val responseData = mutableListOf<PhotoData>()
////            responseData.addAll(data)
//
//            LoadResult.Page(
//                data = responseData,
//                prevKey = if (currentPage == 1) null else -1,
//                nextKey = currentPage.plus(1)
//            )
//
//            // todo remove
//            LoadResult.Error(IllegalArgumentException())
//        } catch (exception:HttpException){
//            LoadResult.Error(exception)
//        } catch (e:Exception){
//        LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, DataNews>): Int? {
//        return null
//    }
//
//}