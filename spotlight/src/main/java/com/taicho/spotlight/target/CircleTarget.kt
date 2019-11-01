package com.taicho.spotlight.target

import android.graphics.Path
import android.view.View
import kotlin.math.max

class CircleTarget(view: View) : ViewTarget(view) {

    override fun getPath(): Path {
        val point = getCenter()

        return Path().apply {
            addCircle(point.x.toFloat(), point.y.toFloat(), getRadius(), Path.Direction.CW)
        }
    }

    private fun getRadius(): Float {
        val bounds = getBounds()
        return max(bounds.exactCenterX(), bounds.exactCenterY())
    }
}
