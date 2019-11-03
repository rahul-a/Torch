package com.taicho.torch.target

import android.graphics.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import com.taicho.torch.gravity.Gravity
import com.taicho.torch.gravity.NO_GRAVITY

abstract class ViewTarget(private val view: View, val name: String, val details: Details) {

    val targetRect by lazy {
        val left = center.x - view.measuredWidth.div(2F)
        val top = center.y - view.measuredHeight.div(2F)
        val right = center.x + view.measuredWidth.div(2F)
        val bottom = center.y + view.measuredHeight.div(2F)

        RectF(left, top, right, bottom)
    }

    val center by lazy {
        val location = getLocation()

        val x = location[0] + view.measuredWidth.toFloat() / 2
        val y = location[1] + view.measuredHeight.toFloat() / 2

        Point(x.toInt(), y.toInt())
    }

    abstract fun draw(canvas: Canvas, value: Float, paint: Paint)

    private fun getLocation(): IntArray {
        val location = IntArray(2)
        view.getLocationInWindow(location)
        return location
    }

    abstract class Details(private val gravity: Int = NO_GRAVITY) {
        internal lateinit var dismiss: (() -> Unit)

        abstract fun getView(inflater: LayoutInflater, root: ViewGroup): View

        @CallSuper
        open fun onViewCreated(view: View, targetRect: Rect) {
            val gravity = Gravity.create(view, gravity)
            gravity.apply(view, targetRect)
        }

        fun dismiss() {
            dismiss.invoke()
        }
    }
}