package com.unicorn.visitor.component

object ComponentsHolder {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().build()
    }

}