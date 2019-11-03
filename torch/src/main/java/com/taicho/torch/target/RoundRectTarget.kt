package com.taicho.torch.target

import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import com.taicho.torch.target.holder.TargetHolder

class RoundRectTarget(
    name: String,
    view: View,
    targetHolder: TargetHolder,
    private val cornerRadius: Float
): ViewTarget(view, name, targetHolder) {

    override fun draw(canvas: Canvas, value: Float, paint: Paint) {
        canvas.drawRoundRect(targetRect, cornerRadius, cornerRadius, paint)
    }
}