package com.igorkazakov.user.foursquareclient.utils

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog

object PermissionUtils {

    const val REQUEST_CODE_ACCESS_COARSE_LOCATION = 1
    const val REQUEST_CODE_ACCESS_FINE_LOCATION = 2

    const val ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION

    private const val ERROR_ACCESS_LOCATION = "Требуется разрешение на определение местоположения"

    fun checkPermission(fragment: Fragment, permission: String, requestCode: Int): Boolean {

        var result = false
        if (fragment.isAdded) {
            val currentAPIVersion = Build.VERSION.SDK_INT
            if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(fragment.context!!, permission) != PackageManager.PERMISSION_GRANTED) {

                    if (fragment.shouldShowRequestPermissionRationale(permission)) {

                        var message = ""
                        when (permission) {
                            ACCESS_COARSE_LOCATION -> message = ERROR_ACCESS_LOCATION
                            ACCESS_FINE_LOCATION -> message = ERROR_ACCESS_LOCATION
                        }

                        showDialog(fragment, message, permission, requestCode)

                    } else {
                        fragment.requestPermissions(arrayOf(permission),
                                requestCode)
                    }
                    result = false

                } else {
                    result = true
                }

            } else {
                result = true
            }
        }

        return result
    }

    private fun showDialog(fragment: Fragment,
                           msg: String,
                           permission: String,
                           requestCode: Int) {

        val alertBuilder = AlertDialog.Builder(fragment.context!!)
        alertBuilder.setCancelable(true)
        alertBuilder.setTitle("")
        alertBuilder.setMessage(msg)
        alertBuilder.setPositiveButton(android.R.string.yes) { dialog, which ->

            if (fragment.isAdded) {
                fragment.requestPermissions(arrayOf(permission), requestCode)
            }
        }
        val alert = alertBuilder.create()
        alert.show()
    }
}