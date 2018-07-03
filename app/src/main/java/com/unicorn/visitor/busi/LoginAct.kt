package com.unicorn.visitor.busi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.jpush.android.api.JPushInterface
import com.blankj.utilcode.util.ToastUtils
import com.unicorn.visitor.R
import com.unicorn.visitor.app.dagger2.component.ComponentsHolder
import com.unicorn.visitor.clicks
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

//        etAccount.setText("mishu")
//        etAccount.setText("menwei")
//        etPassword.setText("123456")
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
                        setAlias()
                        Intent(this@LoginAct, MainAct::class.java).let { startActivity(it) }
                        finish()
                    } else {
                        it.message.let { ToastUtils.showShort(it) }
                    }
                },
                onComplete = {}
        )
    }

    private fun setAlias() {
        JPushInterface.setAlias(this, UserInfo.userId) { p0, p1, p2 ->
//            ToastUtils.showShort("注册别名成功 $p1")
        }
    }

}
