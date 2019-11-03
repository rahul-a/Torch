package com.taicho.torch.util

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import com.taicho.torch.R

internal class TorchHelper {

    companion object {

        internal fun getDecorRect(view: View): Rect {
            val outRect = Rect()

            (view.context as? Activity)?.window?.decorView?.let {
                it.getDrawingRect(outRect)
                outRect.bottom = outRect.height() - navigationBarHeight(it.context)
                outRect.top = statusBarHeight(it.context)
            }

            return outRect
        }

        internal fun getRect(view: View): Rect {
            val hMargin = view.marginStart + view.marginEnd
            val vMargin = view.marginBottom + view.marginTop

            return Rect(
                0,
                0,
                view.measuredWidth + hMargin,
                view.measuredHeight + vMargin
            )
        }

        private fun navigationBarHeight(context: Context): Int =
            computeHeight(context, "navigation_bar_height")

        private fun statusBarHeight(context: Context): Int =
            computeHeight(context, "status_bar_height")

        private fun computeHeight(context: Context, name: String): Int {
            var height = 0

            if (Build.VERSION.SDK_INT >= 21) {
                val resources = context.resources
                val resourceId = resources.getIdentifier(name, "dimen", "android")
                if (resourceId > 0) {
                    height = resources.getDimensionPixelSize(resourceId)
                }
            }
            return height
        }
    }
}