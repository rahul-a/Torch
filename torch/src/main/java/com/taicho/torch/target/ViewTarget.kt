package com.taicho.torch.target

import android.graphics.Path
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taicho.torch.gravity.NO_GRAVITY

abstract class ViewTarget(
    val name: String,
    private val view: View,
    val details: Details
) {

    abstract fun getPath(): Path
    open fun onViewCreated(view: View) {}

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

    abstract class Details(internal val gravity: Int = NO_GRAVITY) {
        internal lateinit var dismiss: (() -> Unit)

        abstract fun getView(inflater: LayoutInflater, root: ViewGroup): View

        fun dismiss() {
            dismiss.invoke()
        }
    }
}