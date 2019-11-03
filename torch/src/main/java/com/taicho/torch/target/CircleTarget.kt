package com.taicho.torch.target

import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import com.taicho.torch.target.holder.TargetHolder
import kotlin.math.max

class CircleTarget(name: String, view: View, targetHolder: TargetHolder) :
    ViewTarget(view, name, targetHolder) {

    override fun draw(canvas: Canvas, value: Float, paint: Paint) {
        canvas.drawCircle(center.x.toFloat(), center.y.toFloat(), getRadius() * value, paint)
    }

    private fun getRadius(): Float {
        return max(targetRect.width(), targetRect.height())
    }
}
