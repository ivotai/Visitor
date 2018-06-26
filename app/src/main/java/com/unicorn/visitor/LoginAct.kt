package com.unicorn.visitor

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.unicorn.visitor.R.id.*
import com.unicorn.visitor.component.ComponentsHolder
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_login.*
import kotlinx.android.synthetic.main.activity_main.*

class LoginAct : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_login)

        tvLogin.clicks().subscribe { login() }
    }

    private fun login() {
        val api = ComponentsHolder.appComponent.getGeneralApi()
        val username = etAccount.text.toString()
        val password = etPassword.text.toString()
        api.login(username, password).custom().subscribeBy(
                onError = {
                    it.let { ToastUtils.showShort(it.message) }
                },
                onNext = {
                    if (it.success) {
                        it.currentUser.username.let { ToastUtils.showShort(it) }
                    } else {
                        it.message.let { ToastUtils.showShort(it) }
                    }
                },
                onComplete = {}
        )
    }

}
