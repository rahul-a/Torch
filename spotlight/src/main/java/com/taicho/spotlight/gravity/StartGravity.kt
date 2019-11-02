package com.taicho.spotlight.gravity

import android.graphics.Rect
import android.graphics.RectF

class StartGravity(src: Rect, dst: Rect): Gravity(src, dst) {

    override fun translationX(target: RectF): Float {
        val tX = target.left - src.width()
        if (tX < 0) return (dst.width() - src.width()).toFloat()
        return tX
    }

    override fun translationY(target: RectF)= alignCenterY(target)
}
