package com.taicho.torch.gravity

import android.graphics.Rect
import android.view.View
import com.taicho.torch.util.TorchHelper.Companion.getDecorRect
import com.taicho.torch.util.TorchHelper.Companion.getRect

const val NO_GRAVITY = 0x0
const val GRAVITY_TOP = 0x1
const val GRAVITY_BOTTOM = 0x2
const val GRAVITY_END = 0x4
const val GRAVITY_START = 0x8

internal const val GRAVITY_MASK = 0xf

internal abstract class Gravity(internal val src: Rect, internal val dst: Rect) {

    abstract fun translationX(target: Rect): Float
    abstract fun translationY(target: Rect): Float

    fun apply(view: View, target: Rect) {
        view.translationX = translationX(target)
        view.translationY = translationY(target)
    }

    internal fun alignCenterX(target: Rect): Int {
        val upper = target.centerX() - src.centerX()
        val lower = target.centerX() + src.centerX()

        return normalize(upper, lower, src.width(), dst.width())
    }

    internal fun alignCenterY(target: Rect): Int {
        val upper = target.centerY() - src.centerY()
        val lower = target.centerY() + src.centerY()

        return normalize(upper, lower, src.height(), dst.height())
    }

    private fun normalize(upper: Int, lower: Int, cur: Int, max: Int): Int {
        var z = upper

        if (upper < 0) {
            z = 0
        }

        if (lower > max) {
            z = max - cur
        }
        return z
    }

    companion object {
        @JvmStatic
        internal fun create(view: View, flag: Int): Gravity {
            val src = getRect(view)
            val dst = getDecorRect(view)

            return when (flag.and(GRAVITY_MASK)) {
                GRAVITY_START -> Start(src, dst)
                GRAVITY_END -> End(src, dst)
                GRAVITY_TOP -> Top(src, dst)
                GRAVITY_BOTTOM -> Bottom(src, dst)
                else -> Void(src, dst)
            }
        }
    }
}
