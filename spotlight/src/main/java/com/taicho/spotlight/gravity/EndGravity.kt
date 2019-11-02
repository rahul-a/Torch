package com.taicho.spotlight.gravity

import android.graphics.Rect
import android.graphics.RectF

class EndGravity(src: Rect, dst: Rect): Gravity(src, dst) {

    override fun translationX(target: RectF): Float {
        val tX = target.right
        if (tX  + src.width() > dst.width()) return 0F
        return tX
    }

    override fun translationY(target: RectF) = alignCenterY(target)
}