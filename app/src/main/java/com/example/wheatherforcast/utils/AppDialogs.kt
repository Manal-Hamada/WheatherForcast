package com.example.wheatherforcast.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.wheatherforcast.R
import com.google.android.gms.maps.model.LatLng

class AppDialogs {

    companion object {
        fun setDialog(
            title: String, context: Context,layout:Int
        ): Dialog {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(layout)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val body = dialog.findViewById(R.id.fav_dia_tv) as TextView
            body.text = title
            return dialog

        }
    }
}