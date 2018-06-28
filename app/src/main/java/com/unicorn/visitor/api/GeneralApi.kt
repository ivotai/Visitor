package com.unicorn.visitor.api

import com.unicorn.visitor.model.Leader
import com.unicorn.visitor.model.response.LoginResponse
import com.unicorn.visitor.model.VisitRecord
import com.unicorn.visitor.model.response.PageResponse
import io.reactivex.Observable
import retrofit2.http.*

interface GeneralApi {

    @FormUrlEncoded
    @POST("login/account")
    fun login(@Field("username") username: String, @Field("password") password: String): Observable<LoginResponse>

    @GET("api/v1/leader/app/list")
    fun getAllLeader(): Observable<List<Leader>>

    @Headers("Content-Type: application/json")
    @POST("api/v1/visitRecord/app")
    fun addVisitRecord(@Body visitRecord: VisitRecord): Observable<Any>

    @GET("api/v1/visitRecord/app")
    fun getVisitRecord(@Query("page") page: Int, @Query("pageSize") pageSize: Int = 10): Observable<PageResponse<VisitRecord>>


//    @FormUrlEncoded
//    @POST("login/token")
//    fun loginByToken(@Field("token") token: String): Observable<LoginResponse>

}