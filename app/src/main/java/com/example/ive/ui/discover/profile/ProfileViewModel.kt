package com.example.ive.ui.discover.profile

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

    var listPhoto = MutableLiveData<List<PhotoGallery>?>()
    var listError = MutableLiveData<String>()

     fun getGalleries(username:String) {

        viewModelScope.launch {
            when (val result = photoRepository.getGalleries(username)) {
                is ApiResponse.Error -> listError.postValue(result.error)
                is ApiResponse.Success -> listPhoto.postValue(result.data)
            }
        }
    }

}