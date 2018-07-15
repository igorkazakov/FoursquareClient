package com.igorkazakov.user.foursquareclient.screens.base.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.igorkazakov.user.foursquareclient.screens.progress.FragmentProgress
import com.igorkazakov.user.foursquareclient.screens.progress.LoadingInterface

open class BaseFragment : Fragment() {

    private var mProgress: LoadingInterface? = null
    //private lateinit var loadingViewModel: LoadingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mProgress = FragmentProgress(activity, container)

//        loadingViewModel = ViewModelProviders.of(this, ViewModelFactory())
//                .get(LoadingViewModel::class.java)
//
//        baseViewModel.loadingLiveData.observe(this, Observer {
//
//            if (it!!) showLoading() else hideLoading()
//        })

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun showLoading() {
        mProgress?.showLoading()
    }

    fun hideLoading() {
        mProgress?.hideLoading()
    }
}