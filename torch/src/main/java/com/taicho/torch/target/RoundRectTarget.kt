package com.taicho.torch.target

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.View

open class RoundRectTarget(
    name: String, view: View,
    details: Details,
    private val cornerRadius: Float
): ViewTarget(view, name, details) {

    override fun draw(canvas: Canvas, value: Float, paint: Paint) {
        canvas.save()
        canvas.translate(rawLocation()[0].toFloat(), rawLocation()[1].toFloat())
        canvas.drawRoundRect(RectF(bounds), cornerRadius, cornerRadius, paint)
        canvas.restore()
    }
}