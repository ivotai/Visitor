package com.unicorn.visitor.base

import android.annotation.SuppressLint
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.unicorn.visitor.R
import com.unicorn.visitor.custom
import com.unicorn.visitor.model.response.PageResponse
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

abstract class PageFra<T> : BaseFra() {

    abstract val adapter1: BaseQuickAdapter<T, BaseViewHolder>

    abstract val recyclerView1: RecyclerView

    abstract val swipeRefreshLayout1: SwipeRefreshLayout

    abstract fun loadPage(page: Int): Observable<PageResponse<T>>

    private val pageSize = 10

    private val page
        get() = adapter1.data.size / pageSize + 1

    private val compositeDisposable = CompositeDisposable()

    override fun initViews() {
        swipeRefreshLayout1.setOnRefreshListener { loadFirstPage() }
        swipeRefreshLayout1.setColorSchemeResources(R.color.colorPrimary)
        recyclerView1.apply {
            layoutManager = LinearLayoutManager(context)
            adapter1.bindToRecyclerView(this)
            adapter1.setEnableLoadMore(true)
            adapter1.setOnLoadMoreListener({ loadNextPage() }, recyclerView1)
        }
        loadFirstPage()
    }

    @SuppressLint("CheckResult")
    private fun loadFirstPage() {
        adapter1.data.clear()
        swipeRefreshLayout1.isRefreshing = true
        loadPage(page)
                .custom()
                .subscribeBy(
                        onNext = {
                            swipeRefreshLayout1.isRefreshing = false
                            adapter1.setNewData(it.content)
                            if (it.last) {
                                adapter1.loadMoreEnd()
                            }
                        },
                        onError = {
                            swipeRefreshLayout1.isRefreshing = false
                        }
                )
                .let { compositeDisposable.add(it) }
    }

    @SuppressLint("CheckResult")
    private fun loadNextPage() {
        loadPage(page)
                .custom()
                .subscribeBy(
                        onNext = {
                            swipeRefreshLayout1.isRefreshing = false
                            adapter1.loadMoreComplete()
                            adapter1.addData(it.content)
                            adapter1.notifyDataSetChanged()
                            if (it.last) {
                                adapter1.loadMoreEnd()
                            }
                        },
                        onError = {
                            swipeRefreshLayout1.isRefreshing = false
                        }
                )
                .let { compositeDisposable.add(it) }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

}