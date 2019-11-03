package com.taicho.torch.target

import android.graphics.Path
import android.view.View
import kotlin.math.max

class CircleTarget(name: String, view: View, details: Details) : ViewTarget(name, view, details) {

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
