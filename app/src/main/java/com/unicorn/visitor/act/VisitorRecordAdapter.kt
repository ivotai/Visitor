package com.unicorn.visitor.act

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.unicorn.visitor.R
import com.unicorn.visitor.model.VisitRecord

class VisitorRecordAdapter : BaseQuickAdapter<VisitRecord, BaseViewHolder>(R.layout.item_visit_record) {

    override fun convert(helper: BaseViewHolder, item: VisitRecord) {
        helper.setText(R.id.tvHello, "${item.visitor.name}访问${item.leader.name}")
    }

}