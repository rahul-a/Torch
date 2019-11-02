package com.taicho.spotlight

import android.content.Context
import android.graphics.*
import android.view.View
import android.view.View.MeasureSpec.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import com.taicho.spotlight.gravity.Gravity
import com.taicho.spotlight.target.Target
import com.taicho.spotlight.util.OverlayHelper.Companion.navigationBarHeight
import com.taicho.spotlight.util.OverlayHelper.Companion.statusBarHeight

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
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(maskColor, PorterDuff.Mode.OVERLAY)
        canvas.drawPath(target, targetPaint)
    }

    fun add(target: Target) {
        this.target.addPath(target.getPath())
        invalidate()
    }

    fun addDescription(description: Spotlight.Description) {
        val targetRect = RectF()
        val view = measureAndGet(description)

        addView(view)
        target.computeBounds(targetRect, true)

        applyGravity(view, description, targetRect)
        description.onViewCreated(view, targetRect)
    }

    private fun measureAndGet(description: Spotlight.Description): View {
        val view = description.getView(this)
        val widthSpec = makeMeasureSpec(measuredWidth, UNSPECIFIED)
        val heightSpec = makeMeasureSpec(measuredHeight, UNSPECIFIED)

        measureChild(view, widthSpec, heightSpec)
        return view
    }

    private fun applyGravity(view: View, description: Spotlight.Description, targetRect: RectF) {
        Gravity.create(getSrc(view), getDst(), description.gravity).apply(view, targetRect)
    }

    private fun getDst(): Rect {
        val decorHeight = navigationBarHeight(context) + statusBarHeight(context)
        return Rect(0, 0, measuredWidth, measuredHeight - decorHeight)
    }

    private fun getSrc(view: View): Rect {
        val hMargin = view.marginStart + view.marginEnd
        val vMargin = view.marginBottom + view.marginTop

        val width = view.measuredWidth + hMargin
        val height = view.measuredHeight + vMargin

        return Rect(0, 0, width, height)
    }
}