package com.unicorn.visitor.app.dagger2.component

object ComponentsHolder {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().build()
    }

}