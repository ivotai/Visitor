package com.unicorn.visitor.act

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.unicorn.visitor.R
import com.unicorn.visitor.component.ComponentsHolder
import com.unicorn.visitor.custom
import com.unicorn.visitor.model.ProcessInfo
import com.unicorn.visitor.model.VisitRecord
import io.reactivex.rxkotlin.subscribeBy

class VisitorRecordAdapter : BaseQuickAdapter<VisitRecord, BaseViewHolder>(R.layout.item_visit_record) {

    override fun convert(helper: BaseViewHolder, item: VisitRecord) {
        helper.setText(R.id.tvHello, "${item.visitor.name}访问${item.leader.name} ${item.status}")
        helper.setOnClickListener(R.id.tvHello) { process(item.objectId) }
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