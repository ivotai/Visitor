package com.unicorn.visitor.model

import java.io.Serializable

/**
 *   2018/6/27: 由 thinkpad 创建
 */
class Visitor(val objectId: String="",
              val name: String,
              val gender: String,
              val idCard: String,
              val company: String,
               val blacklist: Int=0): Serializable
