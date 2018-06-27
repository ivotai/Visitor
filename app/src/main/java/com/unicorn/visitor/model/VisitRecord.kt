package com.unicorn.visitor.model

/**
 *   2018/6/27: 由 thinkpad 创建
 */
data class VisitRecord(val visitor: Visitor, val leader: Leader,
                       val reserveTime: Long, val visitTime: Long,
                       val status: Int = 1)
//status：{ 1.未处理 2.同意来访 3.拒绝来访 4.列入黑名单 }