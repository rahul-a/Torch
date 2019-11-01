package com.taicho.spotlight

import android.content.Context
import android.graphics.*
import android.os.Build
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import com.taicho.spotlight.target.Target

private const val TRANSLUCENT_COLOR = "#80000000"

internal class Overlay(context: Context) : FrameLayout(context) {

    private val maskColor: Int = Color.parseColor(TRANSLUCENT_COLOR)

    private val targetPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.TRANSPARENT
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
        }
    }

    private var target: Path = Path()

    init {
        layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)

        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        setWillNotDraw(false)

        var bottomPadding = 0
        if (Build.VERSION.SDK_INT >= 21) {
            val resources = context.resources
            val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
            if (resourceId > 0) {
                bottomPadding += resources.getDimensionPixelSize(resourceId)
            }
        }

        setPadding(0, 0, 0, bottomPadding)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(maskColor, PorterDuff.Mode.OVERLAY)
        canvas.drawPath(target, targetPaint)
    }

    fun add(target: Target) {
        post {
            this.target.addPath(target.getPath())
            invalidate()
        }
    }
}