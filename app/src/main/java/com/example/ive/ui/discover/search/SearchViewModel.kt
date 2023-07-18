package com.example.ive.ui.discover.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ive.component.model.DataNews
import com.example.ive.network.ApiResponse
import com.example.ive.network.model.PhotoData
import com.example.ive.network.model.PhotoDataList
import com.example.ive.network.model.toDataNews
import com.example.ive.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
):ViewModel() {

    var listPhoto = MutableLiveData<PhotoDataList>()
    var error = MutableLiveData<String>()

    fun getPhotos(request:String){
        if (request != "")  {
        viewModelScope.launch {
            when (val result = photoRepository.getSearchPhoto(request)){
                is ApiResponse.Error -> error.postValue(result.error)
                is ApiResponse.Success -> listPhoto.postValue(result.data?: null)
            }
        }

        } else {
            error.postValue(request)
        }
    }

}