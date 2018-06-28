package com.unicorn.visitor.base

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.unicorn.visitor.R
import com.unicorn.visitor.act.BlacklistAdapter
import com.unicorn.visitor.act.VisitorRecordAdapter
import com.unicorn.visitor.component.ComponentsHolder
import com.unicorn.visitor.model.VisitRecord
import com.unicorn.visitor.model.Visitor
import com.unicorn.visitor.model.response.PageResponse
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fra_blacklist.*

/**
 *   2018/6/28: 由 thinkpad 创建
 */
class BlacklistFra : PageFra<Visitor>() {

    override val layoutID = R.layout.fra_blacklist

    override val adapter1 = BlacklistAdapter()

    override val recyclerView1: RecyclerView
        get() = recyclerView

    override val swipeRefreshLayout1: SwipeRefreshLayout
        get() = swipeRefreshLayout

    override fun loadPage(page: Int): Observable<PageResponse<Visitor>> {
        val api = ComponentsHolder.appComponent.getGeneralApi()
        return api.getBlacklist(page)
    }

}