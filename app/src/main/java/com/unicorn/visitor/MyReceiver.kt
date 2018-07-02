package com.unicorn.visitor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import cn.jpush.android.api.JPushInterface
import com.blankj.utilcode.util.ToastUtils
import org.json.JSONObject


class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        ToastUtils.showShort("hehe")
        if (JPushInterface.ACTION_NOTIFICATION_OPENED == intent.action) {

            val bundle = intent.extras
            val jsonString = bundle.getString(JPushInterface.EXTRA_EXTRA)
            val jsonObject = JSONObject(jsonString)
//            val caseId = jsonObject.getString("caseId")

//            Intent(context, ReceivingCaseAct::class.java).apply {
//                putExtra("caseId", caseId)
//                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//            }.let { context.startActivity(it) }

        }
    }

}