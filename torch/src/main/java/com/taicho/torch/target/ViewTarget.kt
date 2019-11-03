package com.taicho.torch.target

import android.graphics.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import com.taicho.torch.gravity.Gravity
import com.taicho.torch.gravity.NO_GRAVITY

abstract class ViewTarget(private val view: View, val name: String, val details: Details) {

    val bounds by lazy {
        Rect(0, 0, view.measuredWidth, view.measuredHeight)
    }

    val center by lazy {
        val location = getLocation()

        val x = location[0] + view.measuredWidth.toFloat() / 2
        val y = location[1] + view.measuredHeight.toFloat() / 2

        Point(x.toInt(), y.toInt())
    }

    abstract fun draw(canvas: Canvas, value: Float, paint: Paint)

    @CallSuper
    open fun onViewCreated(view: View) {
        val gravity = Gravity.create(view, details.gravity)
        gravity.apply(view, getTargetRect())
    }

    fun rawLocation(): IntArray {
        return getLocation()
    }

    protected fun getTargetRect(): Rect {
        val left = center.x - bounds.centerX()
        val top = center.y - bounds.centerY()
        val right = center.x + bounds.centerX()
        val bottom = center.y + bounds.centerY()
        return Rect(left, top, right, bottom)
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