package com.igorkazakov.user.foursquareclient.screens.viewModel

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.igorkazakov.user.foursquareclient.application.MyApplication
import com.igorkazakov.user.foursquareclient.screens.progress.LoadingInterface

open class LoadingViewModel(application: MyApplication) : AndroidViewModel(application), LoadingInterface {

    val loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()

    override fun showLoading() {
        loadingLiveData.value = true
    }

    override fun hideLoading() {
        loadingLiveData.value = false
    }
}