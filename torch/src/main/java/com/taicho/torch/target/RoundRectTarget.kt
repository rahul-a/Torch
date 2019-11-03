package com.taicho.torch.target

import android.graphics.Path
import android.graphics.RectF
import android.view.View

open class RoundRectTarget(
    name: String, view: View,
    details: Details,
    private val cornerRadius: Float
): ViewTarget(name, view, details) {

    override fun getPath(): Path {
        return Path().apply {
            val rawLocation = rawLocation()
            addRoundRect(RectF(getBounds()), cornerRadius, cornerRadius, Path.Direction.CW)
            offset(rawLocation[0].toFloat(), rawLocation[1].toFloat())
        }
    }
}