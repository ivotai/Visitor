package com.unicorn.visitor.app.dagger2.component

import com.unicorn.visitor.app.api.GeneralApi
import com.unicorn.visitor.app.dagger2.module.ApiModule
import com.unicorn.visitor.app.dagger2.module.ConfigModule
import com.unicorn.visitor.app.dagger2.module.RetrofitModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ConfigModule::class, RetrofitModule::class, ApiModule::class])
interface AppComponent {

    fun getGeneralApi(): GeneralApi

}