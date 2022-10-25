package com.arhamsoft.deskilz.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.arhamsoft.deskilz.R


class LoadingDialog(val mActivity: Activity) {

    var dialog: Dialog? = null
    fun startLoading() {
        dialog = Dialog(mActivity)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.loading_bar)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)
        dialog?.show()
    }

    fun isDismiss() {
                dialog?.dismiss()
    }
}


