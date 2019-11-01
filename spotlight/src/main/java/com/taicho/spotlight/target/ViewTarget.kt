package com.taicho.spotlight.target

import android.graphics.Point
import android.graphics.Rect
import android.view.View

abstract class ViewTarget(private val view: View): Target {

    fun getCenter(): Point {
        val location = getLocation()

        val x = location[0] + view.measuredWidth.toFloat() / 2
        val y = location[1] + view.measuredHeight.toFloat() / 2

        return Point(x.toInt(), y.toInt())
    }

    fun getBounds(): Rect {
        return Rect(0, 0, view.measuredWidth, view.measuredHeight)
    }

    fun rawLocation(): IntArray {
        return getLocation()
    }

    private fun getLocation(): IntArray {
        val location = IntArray(2)
        view.getLocationInWindow(location)
        return location
    }
}