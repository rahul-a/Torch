package com.taicho.spotlight.gravity

import android.graphics.Rect
import android.graphics.RectF

class BottomGravity(src: Rect, dst: Rect): Gravity(src, dst) {

    override fun translationY(target: RectF): Float {
        val tY = target.bottom
        if (tY + src.height() > dst.height()) return 0F
        return tY
    }

    override fun translationX(target: RectF) = alignCenterX(target)
}