package com.unicorn.visitor.act

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.unicorn.visitor.R
import com.unicorn.visitor.R.id.etAccount
import com.unicorn.visitor.clicks
import com.unicorn.visitor.component.ComponentsHolder
import com.unicorn.visitor.custom
import com.unicorn.visitor.model.UserInfo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_login.*

class LoginAct : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_login)

        tvLogin.clicks().subscribe { login() }

        etAccount.setText("mishu")
//        etAccount.setText("menwei")
        etPassword.setText("123456")
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
                        UserInfo.loginResponse = it
                        Intent(this@LoginAct, GuardAct::class.java).let { startActivity(it) }
                        finish()
                    } else {
                        it.message.let { ToastUtils.showShort(it) }
                    }
                },
                onComplete = {}
        )
    }

}
