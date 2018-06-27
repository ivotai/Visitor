package com.unicorn.visitor.api

import com.unicorn.visitor.model.LoginResponse
import com.unicorn.visitor.model.VisitRecordInfo
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface GeneralApi {

    @FormUrlEncoded
    @POST("login/account")
    fun login(@Field("username") username: String, @Field("password") password: String): Observable<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("api/v1/visitRecord/app")
    fun addVisitRecord(@Body visitRecord: VisitRecordInfo): Observable<Any>

//    @GET("register/verifyCode")
//    fun getVerifyCode(@Query("phoneNo") phoneNo: String): Single<Any>

//    @FormUrlEncoded
//    @POST("login/token")
//    fun loginByToken(@Field("token") token: String): Observable<LoginResponse>

}