package com.unicorn.visitor.api

import com.unicorn.visitor.model.Leader
import com.unicorn.visitor.model.ProcessInfo
import com.unicorn.visitor.model.VisitRecord
import com.unicorn.visitor.model.Visitor
import com.unicorn.visitor.model.response.LoginResponse
import com.unicorn.visitor.model.response.PageResponse
import com.unicorn.visitor.model.response.SuccessResponse
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
    fun getVisitRecord(@Query("page") page: Int, @Query("pageSize") pageSize: Int = 10,
                       @Query("keyword") keyword: String = ""): Observable<PageResponse<VisitRecord>>

    @GET("api/v1/visitor/app/blacklist")
    fun getBlacklist(@Query("page") page: Int, @Query("pageSize") pageSize: Int = 10,
                     @Query("keyword") keyword: String = ""): Observable<PageResponse<Visitor>>

//    @FormUrlEncoded
    @POST("api/v1/visitRecord/{visitRecordId}/process")
    fun processVisitRecord(@Path("visitRecordId") visitRecordId: String,
                           @Body processInfo: ProcessInfo): Observable<SuccessResponse>


//    @FormUrlEncoded
//    @POST("login/token")
//    fun loginByToken(@Field("token") token: String): Observable<LoginResponse>

}