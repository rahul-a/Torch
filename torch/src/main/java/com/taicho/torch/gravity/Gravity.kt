package com.taicho.torch.gravity

import android.graphics.Rect
import android.graphics.RectF
import android.view.View

const val GRAVITY_START = 0x8
const val GRAVITY_END = 0x4
const val GRAVITY_BOTTOM = 0x2
const val GRAVITY_TOP = 0x1
const val NO_GRAVITY = 0x0

internal const val GRAVITY_MASK = 0xf

abstract class Gravity(internal val src: Rect, internal val dst: Rect) {

    abstract fun translationX(target: RectF): Float
    abstract fun translationY(target: RectF): Float

    fun apply(view: View, target: RectF) {
        view.translationX = translationX(target)
        view.translationY = translationY(target)
    }

    internal fun alignCenterX(target: RectF): Float {
        val upper = target.centerX() - src.centerX()
        val lower = target.centerX() + src.centerX()

        return normalize(upper, lower, src.width(), dst.width())
    }

    internal fun alignCenterY(target: RectF): Float {
        val upper = target.centerY() - src.centerY()
        val lower = target.centerY() + src.centerY()

        return normalize(upper, lower, src.height(), dst.height())
    }

    private fun normalize(upper: Float, lower: Float, cur: Int, max: Int): Float {
        var z = upper

        if (upper < 0) {
            z = 0F
        }

        if (lower > max) {
            z = (max - cur).toFloat()
        }
        return z
    }

    companion object {
        @JvmStatic
        fun create(src:Rect, dst: Rect, flag: Int): Gravity {
            val voidGravity = object : Gravity(src, dst) {
                override fun translationY(target: RectF) = 0F
                override fun translationX(target: RectF) = 0F
            }

            return when(flag.and(GRAVITY_MASK)) {
                GRAVITY_START -> Start(src, dst)
                GRAVITY_END -> End(src, dst)
                GRAVITY_TOP -> Top(src, dst)
                GRAVITY_BOTTOM -> Bottom(src, dst)
                else -> voidGravity
            }
        }
    }
}

internal class Start(src: Rect, dst: Rect): Gravity(src, dst) {

    override fun translationX(target: RectF): Float {
        val tX = target.left - src.width()
        if (tX < 0) return (dst.width() - src.width()).toFloat()
        return tX
    }

    override fun translationY(target: RectF)= alignCenterY(target)
}

internal class Top(src: Rect, dst: Rect): Gravity(src, dst) {

    override fun translationY(target: RectF): Float {
        val tY = target.top - src.height()
        if (tY < 0) return (dst.height() - src.height()).toFloat()
        return tY
    }

    override fun translationX(target: RectF) = alignCenterX(target)
}

internal class Bottom(src: Rect, dst: Rect): Gravity(src, dst) {

    override fun translationY(target: RectF): Float {
        val tY = target.bottom
        if (tY + src.height() > dst.height()) return 0F
        return tY
    }

    override fun translationX(target: RectF) = alignCenterX(target)
}

internal class End(src: Rect, dst: Rect): Gravity(src, dst) {

    override fun translationX(target: RectF): Float {
        val tX = target.right
        if (tX  + src.width() > dst.width()) return 0F
        return tX
    }

    override fun translationY(target: RectF) = alignCenterY(target)
}
