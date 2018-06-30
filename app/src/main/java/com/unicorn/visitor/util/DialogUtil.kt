package com.unicorn.visitor.util

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog

class DialogUtil() {

    companion object {

        fun showMask(context: Context, title: String) = MaterialDialog.Builder(context)
                .title(title)
                .content("请稍后")
                .progress(true, 0)
                .cancelable(false)
                .show()!!

        fun showConfirm(context: Context, title: String, onPositive: MaterialDialog.SingleButtonCallback) = MaterialDialog.Builder(context)
                .title(title)
                .positiveText("确认")
                .negativeText("取消")
                .onPositive(onPositive)
                .show()!!

    }

}