package com.unicorn.visitor.app.dagger2.module

import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ConfigModule {

    @Singleton
    @Provides
    @Named(value = "baseUrl")
    fun baseUrl() = "http://114.86.225.99:8000/sxv/"

}