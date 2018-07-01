package com.unicorn.visitor.act

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.unicorn.visitor.R
import com.unicorn.visitor.model.Visitor

class BlacklistAdapter : BaseQuickAdapter<Visitor, BaseViewHolder>(R.layout.item_blacklist) {

    override fun convert(helper: BaseViewHolder, item: Visitor) {
    }

}