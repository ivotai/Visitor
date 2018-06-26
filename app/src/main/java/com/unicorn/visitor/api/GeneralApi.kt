package com.unicorn.visitor.api

import com.unicorn.visitor.model.LoginResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*


interface GeneralApi {

//    @GET("register/verifyCode")
//    fun getVerifyCode(@Query("phoneNo") phoneNo: String): Single<Any>

//    @Headers("Content-Type: application/json")
//    @POST("register/mobile")
//    fun register(@Body registerInfo: RegisterInfo): Single<Any>

    @FormUrlEncoded
    @POST("login/account")
    fun login(@Field("username") username: String, @Field("password") password: String): Observable<LoginResponse>

//    @FormUrlEncoded
//    @POST("login/token")
//    fun loginByToken(@Field("token") token: String): Observable<LoginResponse>

}