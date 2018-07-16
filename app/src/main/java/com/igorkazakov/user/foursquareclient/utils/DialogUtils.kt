package com.igorkazakov.user.foursquareclient.utils

import android.content.Context
import android.support.v7.app.AlertDialog
import com.igorkazakov.user.foursquareclient.data.common.ErrorModel

object DialogUtils {

    fun showErrorDialog(context: Context, model: ErrorModel) {

        val builder = AlertDialog.Builder(context)

        builder.setTitle(model.title)
        builder.setMessage(model.message)

        builder.setPositiveButton("Ok"){ dialog, which ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}