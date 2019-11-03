package com.taicho.torch

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import com.taicho.torch.target.ViewTarget

private const val TRANSLUCENT_COLOR = "#80000000"

@SuppressLint("ViewConstructor")
internal class Overlay(context: Context, private val torchListener: TorchListener) :
    FrameLayout(context) {

    private val maskColor: Int = Color.parseColor(TRANSLUCENT_COLOR)

    private val targetPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.TRANSPARENT
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
        }
    }

    private var target: ViewTarget? = null

    init {
        layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)

        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(maskColor, PorterDuff.Mode.OVERLAY)
        target?.draw(canvas, 1F, targetPaint)
    }

    fun render(viewTarget: ViewTarget) {
        val detailsView = targetDetails(viewTarget)
        target = viewTarget
        addLayoutListener(viewTarget, detailsView)
        addView(detailsView)
    }

    private fun addLayoutListener(viewTarget: ViewTarget, detailsView: View) {
        val onLayout = {
            viewTarget.onViewCreated(detailsView)
            viewTarget.details.dismiss = { torchListener.onHide(viewTarget.name) }
            torchListener.onShow(viewTarget.name)
        }

        viewTreeObserver.addOnGlobalLayoutListener(GlobalLayoutListener(onLayout))
    }

    private fun targetDetails(viewTarget: ViewTarget) =
        viewTarget.details.getView(LayoutInflater.from(context), this)

    inner class GlobalLayoutListener(private val onLayout: () -> Unit) :
        ViewTreeObserver.OnGlobalLayoutListener {

        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            onLayout()
        }
    }
}
