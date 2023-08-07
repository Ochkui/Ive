package com.example.ive.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ive.api.PhotoApi
import com.example.ive.component.model.DataNews
import com.example.ive.network.model.PhotoData
import com.example.ive.network.model.toDataNews
import com.example.ive.repository.PhotoRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

class PhotoNewsPagingSource @Inject constructor(
    private val repository: PhotoRepository,
    private val pageSize: Int
    ): PagingSource<Int,DataNews>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataNews> {

        val currentPage = params.key ?: 1
        return try {
            val users = repository.getPhotos(
                page = currentPage,
                pageSize = params.loadSize
            )
            when (users) {
                is ApiResponse.Success -> {

                    Log.i("PhotoNewsPagingSource", "${params.loadSize} = size")
                    return LoadResult.Page(
                        data = users.data.map { it.toDataNews() },
                        prevKey = if (currentPage == 1) null else currentPage -1,
                        nextKey = if (users.data.size == params.loadSize) currentPage + (params.loadSize / pageSize) else null
                    )
                }
                is ApiResponse.Error -> {
                    Log.i("PhotoNewsPagingSource", users.error)
                    LoadResult.Error(IllegalArgumentException())
                }
            }
        }
        catch (e:Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DataNews>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        // convert item index to page index:
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        // page doesn't have 'currentKey' property, so need to calculate it manually:
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }
}