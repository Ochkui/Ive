package com.example.ive.ui.discover.home

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

    var listPhoto = MutableLiveData<List<DataNews>>()
    var listError = MutableLiveData<String>()

    init {
        getPhoto()
    }

    fun getPhoto(){
        viewModelScope.launch{
            when (val result = photoRepository.getPhotos()) {
                is ApiResponse.Error -> listError.postValue(result.error)
                is ApiResponse.Success -> listPhoto.postValue(result.data.map { it.toDataNews()})
            }
        }
    }

}