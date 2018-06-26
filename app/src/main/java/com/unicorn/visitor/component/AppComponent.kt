package com.unicorn.visitor.component

import com.unicorn.visitor.api.GeneralApi
import com.unicorn.visitor.module.ApiModule
import com.unicorn.visitor.module.ConfigModule
import com.unicorn.visitor.module.RetrofitModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ConfigModule::class, RetrofitModule::class, ApiModule::class])
interface AppComponent {

    fun getGeneralApi(): GeneralApi

}