package com.unicorn.visitor.model

import java.util.*

/**
 *   2018/6/27: 由 thinkpad 创建
 */
data class VisitRecordInfo(val visitor: VisitorInfo, val leader: LeaderInfo,
                           val reserveTime: Date, val visitTime: Date,
                           val status: Int = 1)