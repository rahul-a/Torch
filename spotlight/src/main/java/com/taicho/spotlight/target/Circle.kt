package com.taicho.spotlight.target

import android.graphics.Path
import android.graphics.Point
import android.view.View

class Circle(private val view: View,
             private val radius: Float) : Target {

    override fun getPath(): Path {
        val point = getCenter()

        return Path().apply {
            addCircle(point.x.toFloat(), point.y.toFloat(), radius, Path.Direction.CW)
        }
    }

    private fun getCenter(): Point {
        val location = IntArray(2)
        view.getLocationInWindow(location)

        val x = location[0] + view.measuredWidth.toFloat() / 2
        val y = location[1] + view.measuredHeight.toFloat() / 2

        return Point(x.toInt(), y.toInt())
    }
}
