package com.unicorn.visitor.app.dagger2.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.unicorn.visitor.model.UserInfo
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor { chain ->
                val pathSegments = chain.request().url().encodedPathSegments()
                if (pathSegments.contains("login"))
                    chain.proceed(chain.request())
                else
                    chain.request().newBuilder()
                            .addHeader("Cookie", "SESSION=${UserInfo.jsessionid}")
                            .build()
                            .let { chain.proceed(it) }
            }
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(@Named("baseUrl") baseUrl: String, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
//            .addConverterFactory(NobodyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

}