package com.unicorn.visitor.base

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.unicorn.visitor.R
import com.unicorn.visitor.act.VisitorRecordAdapter
import com.unicorn.visitor.component.ComponentsHolder
import com.unicorn.visitor.model.VisitRecord
import com.unicorn.visitor.model.response.PageResponse
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fra_visit_record.*

/**
 *   2018/6/28: 由 thinkpad 创建
 */
class VisitorRecordFra : PageFra<VisitRecord>() {

    override val layoutID = R.layout.fra_visit_record

    override val adapter1 = VisitorRecordAdapter()

    override val recyclerView1: RecyclerView
        get() = recyclerView

    override val swipeRefreshLayout1: SwipeRefreshLayout
        get() = swipeRefreshLayout

    override fun loadPage(page: Int): Observable<PageResponse<VisitRecord>> {
        val api = ComponentsHolder.appComponent.getGeneralApi()
        return api.getVisitRecord(page)
    }

}