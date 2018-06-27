package com.unicorn.visitor.model

import com.unicorn.visitor.model.response.LoginResponse

object UserInfo {

    lateinit var loginResponse: LoginResponse

    val jsessionid get() = loginResponse.jsessionid

    val loginToken get() = loginResponse.loginToken

    var isLogin = false

    val username get() = loginResponse.currentUser.username

    val userId:String get() = loginResponse.currentUser.userId

}