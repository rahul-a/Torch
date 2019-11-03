package com.taicho.torch

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.toRect
import com.taicho.torch.target.ViewTarget

@SuppressLint("ViewConstructor")
internal class Overlay(context: Context, private val torchListener: TorchListener) :
    FrameLayout(context) {

    private val maskColor: Int = ContextCompat.getColor(context, R.color.torch_translucent)

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
        target = viewTarget
        addLayoutListener(viewTarget)
    }

    private fun addLayoutListener(viewTarget: ViewTarget) {
        val details = viewTarget.targetHolder
        val targetRect = viewTarget.targetRect.toRect()
        val name = viewTarget.name


        val view = details.create(LayoutInflater.from(context), this)
        val onLayout = {
            details.onBind(view, targetRect)
            details.dismiss = { torchListener.onHide(name) }
            torchListener.onShow(name)
        }

        addView(view)
        viewTreeObserver.addOnGlobalLayoutListener(GlobalLayoutListener(onLayout))
    }

    inner class GlobalLayoutListener(private val onLayout: () -> Unit) :
        ViewTreeObserver.OnGlobalLayoutListener {

        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            onLayout()
        }
    }
}

