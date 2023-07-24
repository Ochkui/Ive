package com.example.ive.ui.discover.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ive.component.model.DataNews
import com.example.ive.network.ApiResponse
import com.example.ive.network.model.toDataNews
import com.example.ive.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
): ViewModel() {

    sealed class UiState{
        data class Error(val message: String): UiState()
        object Loading : UiState()

        object None : UiState()
    }

    private val _news = MutableLiveData<List<DataNews>>()
    val news: LiveData<List<DataNews>> = _news

    private val _popular = MutableLiveData<List<DataNews>>()
    val popular: LiveData<List<DataNews>> = _popular

    private var currentPage: Int = 0

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    init {
        getPhotoPopular()
        getPhotoNews()
    }

    private fun getPhotoNews(){
        _uiState.postValue(UiState.Loading)
        viewModelScope.launch{
            when (val result = photoRepository.getPhotos()) {

                is ApiResponse.Error -> _uiState.postValue(UiState.Error(result.error))
                is ApiResponse.Success -> {
                    Log.i("HomeViewModel", "give list news")
                    _news.postValue(result.data.map { it.toDataNews() })
                }
            }
        }
    }

    fun getPhotoPopular(){
        ++currentPage
        _uiState.postValue(UiState.Loading)
        viewModelScope.launch{
            when (val result = photoRepository.getPhotos(
                page = currentPage,
                orderBy = "popular"
            )) {
                is ApiResponse.Error -> _uiState.postValue(UiState.Error(result.error))
                is ApiResponse.Success -> {
                    Log.i("HomeViewModel", "give list photo")
                    _popular.postValue(result.data.map { it.toDataNews() })
                }
            }
        }
    }

    fun refresh(){
        currentPage = 0
        getPhotoNews()
        getPhotoPopular()
    }
    fun resetState(){
        _uiState.postValue(UiState.None)
        _news.postValue(emptyList())
        _popular.postValue(emptyList())
    }

}