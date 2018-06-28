package com.unicorn.visitor.act

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.unicorn.visitor.R
import com.unicorn.visitor.model.Visitor

class BlacklistAdapter : BaseQuickAdapter<Visitor, BaseViewHolder>(R.layout.item_visit_record) {

    override fun convert(helper: BaseViewHolder, item: Visitor) {
        helper.setText(R.id.tvHello, item.name)
    }

}