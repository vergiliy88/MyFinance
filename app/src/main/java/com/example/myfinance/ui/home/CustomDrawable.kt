package com.example.myfinance.ui.home

import android.graphics.*
import android.graphics.drawable.Drawable

class CustomDrawable (var events: List<String>) : Drawable() {
    private val mPaint: Paint = Paint()

    init {
        mPaint.strokeWidth = 5f
    }

    override fun draw(canvas: Canvas) {
        val lvl = level
        val b: Rect = bounds
        val x: Float = b.width() * lvl / 10000.0f
        val y: Float = (b.height() - mPaint.strokeWidth) / events.size

        for (i in events.indices) {
            val marginTop = i * 7
            mPaint.color = Color.parseColor(events[i])
            canvas.drawLine(0f, y + marginTop, x, y + marginTop, mPaint)
            canvas.drawLine(x, y + marginTop, b.width().toFloat(), y + marginTop, mPaint)
        }
    }

    override fun onLevelChange(level: Int): Boolean {
        invalidateSelf()
        return true
    }

    override fun setAlpha(alpha: Int) {}
    override fun setColorFilter(cf: ColorFilter?) {}
    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

}