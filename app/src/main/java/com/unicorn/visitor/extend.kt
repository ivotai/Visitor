package com.unicorn.visitor

import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

fun <T> Observable<T>.custom(): Observable<T> {
    return this
            .subscribeOn(Schedulers.io())
            .doOnError {
                val error = if (it is HttpException && it.code() == 400) {
                    it.response().errorBody()?.string().let { JSONObject(it).getString("error") }
                } else it.toString()
                ToastUtils.showShort(error)
            }
            .observeOn(AndroidSchedulers.mainThread())
}

fun View.clicks(): Observable<Any> {
    return RxView.clicks(this).throttleFirst(1, TimeUnit.SECONDS)
}

fun View.clicksWith(view: View): Observable<Any> {
    return RxView.clicks(this)
            .mergeWith(RxView.clicks(view))
            .throttleFirst(1, TimeUnit.SECONDS)
}