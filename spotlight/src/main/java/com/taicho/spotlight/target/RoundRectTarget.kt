package com.taicho.spotlight.target

import android.graphics.Path
import android.graphics.RectF
import android.view.View

class RoundRectTarget(view: View, private val cornerRadius: Float): ViewTarget(view) {

    override fun getPath(): Path {
        return Path().apply {
            val rawLocation = rawLocation()
            addRoundRect(RectF(getBounds()), cornerRadius, cornerRadius, Path.Direction.CW)
            offset(rawLocation[0].toFloat(), rawLocation[1].toFloat())
        }
    }
}