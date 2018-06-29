package com.unicorn.visitor.model.response

/**
 *   2018/6/26: 由 thinkpad 创建
 */

class LoginResponse(
        success: Boolean,
        message: String,
        val currentUser: CurrentUser,
        val jsessionid: String,
        val loginToken: String
) : MessageResponse(success, message)

data class CurrentUser(
        val roleTag: String,
        val roleId: String,
        val roleName: String,
        val userId: String,
        val username: String
)