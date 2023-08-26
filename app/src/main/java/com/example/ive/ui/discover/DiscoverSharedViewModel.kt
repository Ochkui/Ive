package com.example.ive.ui.discover

import androidx.lifecycle.ViewModel
import com.example.ive.utils.NetworkChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiscoverSharedViewModel @Inject constructor(
    val networkChecker: NetworkChecker
): ViewModel() {
    var isConnected = networkChecker

}
