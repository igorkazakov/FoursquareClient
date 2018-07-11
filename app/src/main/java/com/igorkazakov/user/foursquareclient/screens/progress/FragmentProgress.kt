package com.igorkazakov.user.foursquareclient.screens.progress

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.igorkazakov.user.foursquareclient.R

class FragmentProgress(activity: Activity?,
                       private val mContainer: ViewGroup?) : LoadingInterface {

    private var mLoadingView: View? = null
    private var mIsLoading: Boolean = false

    init {

        activity?.let {
            val linearLayout = LinearLayout(activity)
            val linLayoutParam = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT)
            linearLayout.layoutParams = linLayoutParam
            val layoutInflater = activity.layoutInflater
            mLoadingView = layoutInflater.inflate(R.layout.fragment_loading, linearLayout, false)
        }
    }

    override fun showLoading() {

        mLoadingView?.let {
            if (!mIsLoading) {
                mContainer?.addView(mLoadingView)
                mIsLoading = true
            }
        }
    }

    override fun hideLoading() {
        mLoadingView?.let {
            mContainer?.removeView(mLoadingView)
            mIsLoading = false
        }
    }
}