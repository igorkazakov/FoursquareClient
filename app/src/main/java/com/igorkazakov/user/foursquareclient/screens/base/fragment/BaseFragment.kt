package com.igorkazakov.user.foursquareclient.screens.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.igorkazakov.user.foursquareclient.screens.progress.FragmentProgress


open class BaseFragment : MvpAppCompatFragment() {

    private var mProgress: FragmentProgress? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mProgress = FragmentProgress(activity, container)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun showLoading() {
        mProgress?.showLoading()
    }

    fun hideLoading() {
        mProgress?.hideLoading()
    }
}