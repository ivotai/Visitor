package com.unicorn.visitor.main.visitRecord

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.unicorn.visitor.R
import com.unicorn.visitor.app.base.PageFra
import com.unicorn.visitor.clicks
import com.unicorn.visitor.app.dagger2.component.ComponentsHolder
import com.unicorn.visitor.main.visitRecord.event.RefreshVisitRecordListEvent
import com.unicorn.visitor.model.UserInfo
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

    @SuppressLint("CheckResult")
    override fun initViews() {
        super.initViews()
        flAdd.visibility = if (UserInfo.isGuard) View.VISIBLE else View.INVISIBLE
        flAdd.clicks().subscribe { Intent(context, AddVisitRecordAct::class.java).let { startActivity(it) } }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxBus.get().register(this)
    }

    override fun onDestroy() {
        RxBus.get().unregister(this)
        super.onDestroy()
    }

    @Subscribe
    fun onRefreshEvent(event: RefreshVisitRecordListEvent) {
        loadFirstPage()
    }

}