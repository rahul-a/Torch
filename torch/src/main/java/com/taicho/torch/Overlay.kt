package com.taicho.torch

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.LayoutInflater
import android.view.View
import android.view.View.MeasureSpec.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import com.taicho.torch.gravity.Gravity
import com.taicho.torch.target.ViewTarget
import com.taicho.torch.util.OverlayHelper.Companion.navigationBarHeight
import com.taicho.torch.util.OverlayHelper.Companion.statusBarHeight

private const val TRANSLUCENT_COLOR = "#80000000"

@SuppressLint("ViewConstructor")
internal class Overlay(context: Context, private val listener: Listener) : FrameLayout(context) {

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

    fun render(target: ViewTarget) {
        target.details.dismiss = { listener.onHide(target.name) }
        this.target.addPath(target.getPath())

        val detailsView = measureAndGet(target.details)
        addView(detailsView)
        target.onViewCreated(detailsView)
        translate(detailsView, target)

        listener.onShow(target.name)
        invalidate()
    }

    private fun translate(view: View, viewTarget: ViewTarget) {
        val targetRect = RectF()
        target.computeBounds(targetRect, true)
        applyGravity(view, viewTarget.details, targetRect)
    }

    private fun measureAndGet(details: ViewTarget.Details): View {
        val view = details.getView(LayoutInflater.from(context), this)
        val widthSpec = makeMeasureSpec(measuredWidth, UNSPECIFIED)
        val heightSpec = makeMeasureSpec(measuredHeight, UNSPECIFIED)

        measureChild(view, widthSpec, heightSpec)
        return view
    }

    private fun applyGravity(view: View, details: ViewTarget.Details, targetRect: RectF) {
        Gravity.create(getSrc(view), getDst(), details.gravity).apply(view, targetRect)
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