package com.igorkazakov.user.foursquareclient.screens.base.map

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.igorkazakov.user.foursquareclient.screens.base.fragment.BaseFragment
import com.igorkazakov.user.foursquareclient.utils.DialogUtils
import com.igorkazakov.user.foursquareclient.utils.PermissionUtils

abstract class BaseMapFragment : BaseFragment(), BaseMapInterface {

    private var mPresenter: BaseMapPresenter<*>? = null

    abstract fun createPresenter() : BaseMapPresenter<*>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPresenter = createPresenter()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initUpdateLocation() {
        mPresenter?.startLocationUpdates(this)
    }

    override fun showLocationError() {
        context?.let {
            DialogUtils.showErrorDialog(it,
                    "Внимание",
                    "Не удалось определить местоположение, включите передачу геоданных")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {

        if ((requestCode == PermissionUtils.REQUEST_CODE_ACCESS_COARSE_LOCATION ||
                        requestCode == PermissionUtils.REQUEST_CODE_ACCESS_FINE_LOCATION) &&

                grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            mPresenter?.startLocationUpdates(this)

        } else {
            super.onRequestPermissionsResult(requestCode, permissions,
                    grantResults)
        }
    }
}