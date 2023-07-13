package com.example.ive.ui.discover.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ive.R
import com.example.ive.component.model.DataNews
import com.example.ive.component.model.UserProfileViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {

    var listPhoto = MutableLiveData<List<DataNews>>()

    init {
        getPhoto()
    }

    private fun getPhoto(){
        val photos = mutableListOf<DataNews>()
        with(photos) {
            add(
                DataNews(
                    UserProfileViewData(R.drawable.profile_dev, "Stas", "Develop"),
                    R.drawable.im_tets_car
                )
            )
            add(
                DataNews(
                    UserProfileViewData(R.drawable.profile_dev, "Stas", "Develop"),
                    R.drawable.ic_test_car_vertical_1
                )
            )
            add(
                DataNews(
                    UserProfileViewData(R.drawable.profile_dev, "Stas", "Develop"),
                    R.drawable.ic_test_car_2
                )
            )
            add(
                DataNews(
                    UserProfileViewData(R.drawable.profile_dev, "Stas", "Develop"),
                    R.drawable.ic_test_car_2
                )
            )
            add(
                DataNews(
                    UserProfileViewData(R.drawable.profile_dev, "Stas", "Develop"),
                    R.drawable.ic_test_car_vertical_2
                )
            )
            add(
                DataNews(
                    UserProfileViewData(R.drawable.profile_dev, "Stas", "Develop"),
                    R.drawable.ic_test_car_vertical_1
                )
            )
            add(
                DataNews(
                    UserProfileViewData(R.drawable.profile_dev, "Stas", "Develop"),
                    R.drawable.ic_test_car_2
                )
            )
        }
        listPhoto.postValue(photos)
    }

}