package com.taicho.torch.gravity

import android.graphics.Rect


internal class Start(src: Rect, dst: Rect) : Gravity(src, dst) {

    override fun translationX(target: Rect): Float {
        val tX = target.left - src.width()
        if (tX < dst.left) return (dst.width() - src.width()).toFloat()
        return tX.toFloat()
    }

    override fun translationY(target: Rect) = alignCenterY(target).toFloat()
}

internal class Top(src: Rect, dst: Rect) : Gravity(src, dst) {

    override fun translationY(target: Rect): Float {
        val tY = target.top - src.height()
        if (tY < dst.top) return (dst.height() - src.height()).toFloat()
        return tY.toFloat()
    }

    override fun translationX(target: Rect) = alignCenterX(target).toFloat()
}

internal class Bottom(src: Rect, dst: Rect) : Gravity(src, dst) {

    override fun translationY(target: Rect): Float {
        val tY = target.bottom
        if (tY + src.height() > dst.bottom) return 0F
        return tY.toFloat()
    }

    override fun translationX(target: Rect) = alignCenterX(target).toFloat()
}

internal class End(src: Rect, dst: Rect) : Gravity(src, dst) {

    override fun translationX(target: Rect): Float {
        val tX = target.right
        if (tX + src.width() > dst.right) return 0F
        return tX.toFloat()
    }

    override fun translationY(target: Rect) = alignCenterY(target).toFloat()
}

internal class Void(src: Rect, dst: Rect): Gravity(src, dst) {
    override fun translationY(target: Rect) = dst.top.toFloat()
    override fun translationX(target: Rect) = dst.left.toFloat()
}