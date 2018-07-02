package com.unicorn.visitor.model

import com.unicorn.visitor.model.response.LoginResponse

object UserInfo {

    lateinit var loginResponse: LoginResponse

    val jsessionid get() = loginResponse.jsessionid

    val loginToken get() = loginResponse.loginToken

    var isLogin = false

//    val username get() = currentUser.username

    val userId: String get() = currentUser.userId

    val isGuard get() = currentUser.roleTag == "Guard"

    val isSecretary get() = currentUser.roleTag == "Secretary"

    private val currentUser get() = loginResponse.currentUser
}