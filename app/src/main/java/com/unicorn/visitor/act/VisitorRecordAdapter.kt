package com.unicorn.visitor.act

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.unicorn.visitor.R
import com.unicorn.visitor.component.ComponentsHolder
import com.unicorn.visitor.custom
import com.unicorn.visitor.model.ProcessInfo
import com.unicorn.visitor.model.VisitRecord
import io.reactivex.rxkotlin.subscribeBy
import org.joda.time.DateTime

class VisitorRecordAdapter : BaseQuickAdapter<VisitRecord, BaseViewHolder>(R.layout.item_visit_record) {

    override fun convert(helper: BaseViewHolder, item: VisitRecord) {
        helper.setText(R.id.tvVisitorAndLeader, "${item.visitor.name}请求来访${item.leader.name}")
        helper.setText(R.id.tvReserveTime, DateTime(item.reserveTime).toString("yyyy-MM-dd"))
        helper.setText(R.id.tvReason,"1984年原金薮乡龙门大队副支书龚盛家负责架设本大队高、低压输电线路，各生产队农户安装生产、生活用电设备期间，他儿子龚铁山在本大队小学学校代课，")
    }

    private fun process(visitRecordId: String) {
        val api = ComponentsHolder.appComponent.getGeneralApi()
        api.processVisitRecord(visitRecordId, ProcessInfo(2)).custom().subscribeBy(
                onNext = {
                    it
                },
                onError = {
                    it
                }
        )
    }

}