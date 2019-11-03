package com.taicho.torch.util

import android.content.Context
import android.os.Build
import androidx.core.content.ContextCompat
import com.taicho.torch.R

class OverlayHelper {

    companion object {
        @JvmStatic
        fun navigationBarHeight(context: Context): Int =
            computeHeight(context, "navigation_bar_height")

        @JvmStatic
        fun statusBarHeight(context: Context): Int =
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