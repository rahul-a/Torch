package com.taicho.torch.target

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.View

class RoundRectTarget(
    name: String,
    view: View,
    details: Details,
    private val cornerRadius: Float
): ViewTarget(view, name, details) {

    override fun draw(canvas: Canvas, value: Float, paint: Paint) {
        canvas.drawRoundRect(targetRect, cornerRadius, cornerRadius, paint)
    }
}