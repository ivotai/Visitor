package com.unicorn.visitor.app

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.facebook.stetho.Stetho

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        Stetho.initializeWithDefaults(this)
        initJPush()
    }

    private fun initJPush() {
//        JPushInterface.setDebugMode(true)
//        JPushInterface.init(this)
    }

}