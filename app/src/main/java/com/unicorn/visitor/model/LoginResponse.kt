package com.unicorn.visitor.model

/**
 *   2018/6/26: 由 thinkpad 创建
 */

data class LoginResponse(
        val currentUser: CurrentUser,
        val jsessionid: String, //9c203285-86c7-4f96-bc8f-c19ac17046b5
        val success: Boolean, //true
        val loginToken: String, //728a3c39-14f6-4577-b21f-eb3b0044631a
        val message: String
)

data class CurrentUser(
        val userId: String, //2ed9f03e135e47dba9e92bcebc75ea0a
        val username: String //门卫
)