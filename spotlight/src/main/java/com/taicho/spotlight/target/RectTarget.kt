package com.taicho.spotlight.target

import android.graphics.Path
import android.graphics.RectF
import android.view.View

class RectTarget(view: View): ViewTarget(view) {

    override fun getPath(): Path {
        return Path().apply {
            val rawLocation = rawLocation()
            addRect(RectF(getBounds()), Path.Direction.CW)
            offset(rawLocation[0].toFloat(), rawLocation[1].toFloat())
        }
    }
}