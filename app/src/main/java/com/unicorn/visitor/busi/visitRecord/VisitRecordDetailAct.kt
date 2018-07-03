package com.unicorn.visitor.busi.visitRecord

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.ToastUtils
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
import kotlinx.android.synthetic.main.act_visit_record_detial.*
import org.joda.time.DateTime

class VisitRecordDetailAct : AppCompatActivity() {

    private lateinit var visitRecordId: String
    private lateinit var visitRecord: VisitRecord

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_visit_record_detial)

        visitRecordId = intent.getStringExtra("visitRecordId")
        getVisitRecord()

        flBack.clicks().subscribe { finish() }
        tvBlacklist.clicks().subscribe {
            DialogUtil.showConfirm(this@VisitRecordDetailAct, "确认将${visitRecord.visitor.name}加入黑名单",
                    MaterialDialog.SingleButtonCallback { _, _ -> process(4) }
            )
        }
        tvAgree.clicks().subscribe {
            DialogUtil.showConfirm(this@VisitRecordDetailAct, "确认同意该请求",
                    MaterialDialog.SingleButtonCallback { _, _ -> process(2) }
            )
        }
        tvDisagree.clicks().subscribe {
            DialogUtil.showConfirm(this@VisitRecordDetailAct, "确认拒绝该请求",
                    MaterialDialog.SingleButtonCallback { _, _ -> process(3) }
            )
        }
    }

    private fun getVisitRecord() {
        val api = ComponentsHolder.appComponent.getGeneralApi()
        api.getVisitRecord(visitRecordId).custom().subscribeBy(
                onNext = {
                    visitRecord = it
                    fillViews()
                },
                onError = {}
        )
    }

    private fun fillViews() {
        val visitor = visitRecord.visitor
        tvName.text = visitor.name
        tvGender.text = visitor.gender
        tvIdCard.text = visitor.idCard
        tvCompany.text = visitor.company
        tvLeader.text = visitRecord.leader.name
        tvReserveTime.text = DateTime(visitRecord.reserveTime).toString("yyyy-MM-dd")
        tvDescription.text = visitRecord.description
        listOf(ll1, ll2).forEach { it.visibility = if (UserInfo.isSecretary && visitRecord.status == 1) View.VISIBLE else View.GONE }
    }

    private fun process(visitRecordStatus: Int) {
        val api = ComponentsHolder.appComponent.getGeneralApi()
        api.processVisitRecord(visitRecord.objectId, (visitRecordStatus)).custom().subscribeBy(
                onNext = {
                    if (it.success) {
                        ToastUtils.showShort("请求已处理")
                        RxBus.get().post(RefreshVisitRecordListEvent())
                        finish()
                    }
                },
                onError = {
                    it
                }
        )
    }

}
