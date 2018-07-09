package com.igorkazakov.user.foursquareclient.utils

import android.content.Context
import android.support.v7.app.AlertDialog

object DialogUtils {

    fun showErrorDialog(context: Context, title: String = "", message: String = "") {

        val builder = AlertDialog.Builder(context)

        builder.setTitle(title)
        builder.setMessage(message)

        builder.setPositiveButton("Ok"){ dialog, which ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}