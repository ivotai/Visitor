package com.unicorn.visitor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import cn.jpush.android.api.JPushInterface
import com.unicorn.visitor.busi.visitRecord.VisitRecordDetailAct
import org.json.JSONObject


class JPushReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (JPushInterface.ACTION_NOTIFICATION_OPENED == intent.action) {

            val bundle = intent.extras
            val jsonString = bundle.getString(JPushInterface.EXTRA_EXTRA)
            val jsonObject = JSONObject(jsonString)
            val visitRecordId = jsonObject.getString("visitRecordId")

            Intent(context, VisitRecordDetailAct::class.java).apply {
                putExtra("visitRecordId", visitRecordId)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }.let { context.startActivity(it) }

        }
    }

}