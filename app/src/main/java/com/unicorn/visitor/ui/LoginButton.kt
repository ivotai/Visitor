package com.unicorn.visitor.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.TextView
import android.graphics.RectF
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.widget.Button


/**
 *   2018/6/10: 由 thinkpad 创建
 */
class LoginButton(context: Context?, attrs: AttributeSet?) : TextView(context, attrs) {

    private val paint = Paint()
    private lateinit var rectF: RectF

    private fun initPaint() {
        paint.isAntiAlias = true
    }

    init {
        setTextColor(Color.WHITE)
        initPaint()
        gravity = Gravity.CENTER
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        LinearGradient(0f, 0f, measuredWidth.toFloat(), 0f,
                intArrayOf(Color.parseColor("#457ADB"), Color.parseColor("#95C7FF")),
                null, Shader.TileMode.CLAMP)
                .let { paint.shader = it }
        rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        val corner = 1000f
        canvas.drawRoundRect(rectF, corner, corner, paint)
        super.onDraw(canvas)
    }

}