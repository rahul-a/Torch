package com.taicho.spotlight.gravity

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
                GRAVITY_START -> StartGravity(src, dst)
                GRAVITY_END -> EndGravity(src, dst)
                GRAVITY_TOP -> TopGravity(src, dst)
                GRAVITY_BOTTOM -> BottomGravity(src, dst)
                else -> voidGravity
            }
        }
    }
}