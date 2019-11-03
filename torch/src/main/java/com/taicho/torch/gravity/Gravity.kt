package com.taicho.torch.gravity

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import com.taicho.torch.util.OverlayHelper.Companion.navigationBarHeight
import com.taicho.torch.util.OverlayHelper.Companion.statusBarHeight

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
            val src = getSrc(view)
            val dst = getDst(view)

            val voidGravity = object : Gravity(src, dst) {
                override fun translationY(target: Rect) = 0F
                override fun translationX(target: Rect) = 0F
            }

            return when (flag.and(GRAVITY_MASK)) {
                GRAVITY_START -> Start(src, dst)
                GRAVITY_END -> End(src, dst)
                GRAVITY_TOP -> Top(src, dst)
                GRAVITY_BOTTOM -> Bottom(src, dst)
                else -> voidGravity
            }
        }

        private fun getDst(view: View): Rect {
            val outRect = Rect()

            (view.parent as? ViewGroup)?.let {
                it.getDrawingRect(outRect)
                outRect.bottom = outRect.height() - navigationBarHeight(it.context)
                outRect.top = statusBarHeight(it.context)
            }

            return outRect
        }

        private fun getSrc(view: View): Rect {
            val hMargin = view.marginStart + view.marginEnd
            val vMargin = view.marginBottom + view.marginTop

            return Rect(
                0,
                0,
                view.measuredWidth + hMargin,
                view.measuredHeight + vMargin
            )
        }
    }
}

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
