package com.example.ive.ui.discover.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ive.component.model.DataNews
import com.example.ive.network.ApiResponse
import com.example.ive.network.model.toDataNews
import com.example.ive.network.model.toPhotoEntity
import com.example.ive.repository.PhotoRepository
import javax.inject.Inject

class PhotoNewsPagingSource @Inject constructor(
    private val repository: PhotoRepository,
    ): PagingSource<Int,DataNews>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataNews> {

        val currentPage = params.key ?: 1
        return try {
            val users = repository.getPhotos(
                page = currentPage,
                pageSize = params.loadSize,
                orderBy = "latest"
            )
            when (users) {
                is ApiResponse.Success -> {
                    repository.insertPhotoDao(users.data.map { it.toPhotoEntity("latest") })
                    return LoadResult.Page(
                        data = users.data.map { it.toDataNews() },
                        prevKey = if (currentPage == 1) null else currentPage -1,
                        nextKey = if (users.data.size < params.loadSize)  null else currentPage + 1
                    )
                }
                is ApiResponse.Error -> {
                    LoadResult.Error(IllegalArgumentException())
                }
            }
        }
        catch (e:Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DataNews>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}