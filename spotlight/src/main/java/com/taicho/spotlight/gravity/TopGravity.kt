package com.taicho.spotlight.gravity

import android.graphics.Rect
import android.graphics.RectF

class TopGravity(src: Rect, dst: Rect): Gravity(src, dst) {

    override fun translationY(target: RectF): Float {
        val tY = target.top - src.height()
        if (tY < 0) return (dst.height() - src.height()).toFloat()
        return tY
    }

    override fun translationX(target: RectF) = alignCenterX(target)
}