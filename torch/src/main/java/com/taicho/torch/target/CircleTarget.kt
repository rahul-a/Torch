package com.taicho.torch.target

import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import kotlin.math.max

class CircleTarget(name: String, view: View, details: Details) : ViewTarget(view, name, details) {

    override fun draw(canvas: Canvas, value: Float, paint: Paint) {
        canvas.drawCircle(center.x.toFloat(), center.y.toFloat(), getRadius(), paint)
    }

    private fun getRadius(): Float {
        return max(targetRect.width(), targetRect.height())
    }
}
