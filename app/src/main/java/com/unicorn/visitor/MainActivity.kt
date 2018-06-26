package com.unicorn.visitor

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.unicorn.visitor.component.ComponentsHolder
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvHello.clicks().subscribe { login() }
    }

    private fun login() {
        val api = ComponentsHolder.appComponent.getGeneralApi()
        api.login("menwei", "123456").custom().subscribeBy(
                onError = {
                    it
                },
                onNext = {
                    it.currentUser.username.let {
                        ToastUtils.showShort(it)
                    }
                },
                onComplete = {}
        )
    }
}
