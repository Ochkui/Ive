package com.example.ive.ui.discover.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ive.network.ApiResponse
import com.example.ive.network.model.PhotoGallery
import com.example.ive.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
):ViewModel() {

    sealed class UiState {
        data class Error(val message: String): UiState()
        object Loading : UiState()
        object None : UiState()
    }

    private var _uiState = MutableLiveData<UiState>()
    val uiState:LiveData<UiState> = _uiState

    var listPhoto = MutableLiveData<List<PhotoGallery>?>()

     fun getGalleries(username:String) {

         _uiState.postValue(UiState.Loading)
        viewModelScope.launch {
            when (val result = photoRepository.getGalleries(username)) {
                is ApiResponse.Error -> {
                    _uiState.postValue(UiState.Error(result.error))
                }
                is ApiResponse.Success -> {
                    listPhoto.postValue(result.data)
                }
            }
        }
    }

    fun resetState(){
        _uiState.postValue(UiState.None)
        listPhoto.postValue(emptyList())
    }

}