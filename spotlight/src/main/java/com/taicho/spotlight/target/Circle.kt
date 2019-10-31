package com.taicho.spotlight.target

import android.graphics.Path
import android.graphics.Point

class Circle(private val point: Point,
             private val radius: Float) : Target {

    override fun getPath(): Path {
        return Path().apply {
            addCircle(point.x.toFloat(), point.y.toFloat(), radius, Path.Direction.CW)
        }
    }
}
