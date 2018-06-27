package com.unicorn.visitor.model.response

/**
 *   2018/6/27: 由 thinkpad 创建
 */

data class PageResponse<T>(
        val content: List<T>,
        val first: Boolean, //true
        val last: Boolean, //true
        val number: Int, //0
        val numberOfElements: Int, //12
        val size: Int, //2147483647
        val sort: Any, //null
        val totalElements: Int, //12
        val totalPages: Int //1
)