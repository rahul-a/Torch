package com.taicho.spotlight.target

import android.graphics.Path
import android.graphics.Point
import android.view.View

class CircleTarget(view: View, private val radius: Float) : ViewTarget(view) {

    override fun getPath(): Path {
        val point = getCenter()

        return Path().apply {
            addCircle(point.x.toFloat(), point.y.toFloat(), radius, Path.Direction.CW)
        }
    }
}
