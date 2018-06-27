package com.unicorn.visitor.act

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.unicorn.visitor.R
import com.unicorn.visitor.clicks
import com.unicorn.visitor.component.ComponentsHolder
import com.unicorn.visitor.custom
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_login.*

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
