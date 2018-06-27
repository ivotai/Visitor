package com.unicorn.visitor.model.response

/**
 *   2018/6/27: 由 thinkpad 创建
 */
open class MessageResponse(success: Boolean, val message: String) : SuccessResponse(success)