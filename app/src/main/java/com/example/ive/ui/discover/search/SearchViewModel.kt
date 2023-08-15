package com.example.ive.ui.discover.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ive.network.ApiResponse
import com.example.ive.network.model.PhotoDataList
import com.example.ive.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel() {
    sealed class UiState {
        data class Success(
            val data: PhotoDataList,
            val isSeeMoreVisible: Boolean
        ) : UiState()

        data class Error(val message: String) : UiState()
        object Loading : UiState()
        data class None(val isSeeMoreVisible: Boolean) : UiState()
    }

    private var totalPages = 1
    private var currentPage: Int = 1
    private var currentRequest: String = ""

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    fun getPhotos(request: String, isNextPage: Boolean = false) {
        if (currentRequest != request) {
            _uiState.postValue(UiState.Loading)
            currentRequest = request
            currentPage = 1
            getData(request)
        } else if (isNextPage){
            currentPage++
            getData(request)
        }
    }

    private fun getData(request: String) {
        if (request != "" && currentPage <= totalPages) {
            viewModelScope.launch {
                // todo improve
                when (val result = photoRepository.getSearchPhoto(request, 30, currentPage)) {
                    is ApiResponse.Error -> {
                        _uiState.postValue(UiState.Error(result.error))
                    }

                    is ApiResponse.Success -> {
                        result.data.totalPages.let {
                            if (it != 0) {
                                totalPages = it
                                _uiState.postValue(
                                    UiState.Success(
                                        data = result.data,
                                        isSeeMoreVisible = currentPage != totalPages
                                    )
                                )
                            } else {
                                _uiState.postValue(UiState.Error("Nothing found"))
                            }

                        }
                    }
                }
            }

        } else {
            _uiState.postValue(UiState.Error(request))
        }
    }

    fun resetState(){
        _uiState.postValue(UiState.None(currentPage != totalPages))
    }

}