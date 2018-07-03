package com.unicorn.visitor.busi.visitRecord

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hwangjr.rxbus.RxBus
import com.unicorn.visitor.R
import com.unicorn.visitor.app.dagger2.component.ComponentsHolder
import com.unicorn.visitor.busi.visitRecord.event.RefreshVisitRecordListEvent
import com.unicorn.visitor.clicks
import com.unicorn.visitor.custom
import com.unicorn.visitor.model.UserInfo
import com.unicorn.visitor.model.VisitRecord
import com.unicorn.visitor.util.DialogUtil
import io.reactivex.rxkotlin.subscribeBy
import org.joda.time.DateTime

class VisitorRecordAdapter : BaseQuickAdapter<VisitRecord, BaseViewHolder>(R.layout.item_visit_record) {

    override fun convert(helper: BaseViewHolder, item: VisitRecord) {
        helper.apply {
            setText(R.id.tvVisitorAndLeader, "${item.visitor.name}请求来访${item.leader.name} ${item.status}")
            setText(R.id.tvReserveTime, DateTime(item.reserveTime).toString("yyyy-MM-dd"))
            setText(R.id.tvDescription, item.description)

            listOf(R.id.tvAgree, R.id.tvDisagree).forEach {
                getView<View>(it).visibility =
                        if (UserInfo.isSecretary && item.status == 1) View.VISIBLE else View.INVISIBLE
            }

            val tvPrompt = getView<TextView>(R.id.tvPrompt)
            tvPrompt.setTextColor(ContextCompat.getColor(mContext, if (item.status == 1) R.color.md_red_400 else R.color.md_teal_400))
            tvPrompt.text = if (item.status == 1) if (UserInfo.isSecretary) "请尽快处理请求" else "等待秘书处理" else item.getStatusText()

            getView<View>(R.id.tvAgree).clicks().subscribe {
                DialogUtil.showConfirm(mContext, "确认同意该请求",
                        MaterialDialog.SingleButtonCallback { _, _ -> process(item.objectId, 2) }
                )
            }
            getView<View>(R.id.tvDisagree).clicks().subscribe {
                DialogUtil.showConfirm(mContext, "确认拒绝该请求",
                        MaterialDialog.SingleButtonCallback { _, _ -> process(item.objectId, 3) }
                )
            }

            getView<View>(R.id.root).clicks().subscribe {
                Intent(mContext,VisitRecordDetailAct::class.java).apply {
                    putExtra("visitRecord",item)
                }.let { mContext.startActivity(it) }
            }
        }
    }

    private fun process(visitRecordId: String, visitRecordStatus: Int) {
        val api = ComponentsHolder.appComponent.getGeneralApi()
        api.processVisitRecord(visitRecordId, (visitRecordStatus)).custom().subscribeBy(
                onNext = {
                    if (it.success) {
                        ToastUtils.showShort("请求已处理")
                        RxBus.get().post(RefreshVisitRecordListEvent())
                    }
                },
                onError = {
                    it
                }
        )
    }

}