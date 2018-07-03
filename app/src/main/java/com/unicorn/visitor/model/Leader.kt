package com.unicorn.visitor.model

import java.io.Serializable

/**
 *   2018/6/27: 由 thinkpad 创建
 */

data class Leader(
		val objectId: String, //c5d76c49dacb4dc391a6cf3a77951ac1
		val name: String, //院长
		val secretaryId: String, //8a3f8c67f4c749e688823deb1da40a7c
		val secretaryName: String //院长秘书
): Serializable{
	override fun toString(): String {
		return this.name
	}
}