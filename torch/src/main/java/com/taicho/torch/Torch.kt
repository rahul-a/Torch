package com.taicho.torch

import android.app.Activity
import android.content.Context
import android.os.Looper
import android.view.ViewGroup
import com.taicho.torch.target.ViewTarget

class Torch constructor(private val target: ViewTarget) {

    private lateinit var overlay: Overlay

    fun beam(activity: Activity, torchListener: TorchListener) {
        checkMainThread()

        addOverlay(activity, torchListener)
        render()
    }

    private fun render() {
        if (!::overlay.isInitialized) return

        overlay.post {
            overlay.render(target)
        }
    }

    fun hide() {
        if (!::overlay.isInitialized) return

        overlay.let {
            if (it.isAttachedToWindow) {
                (it.parent as ViewGroup).removeView(it)
            }
        }
    }

    private fun getDecorView(context: Context): ViewGroup? =
        (context as? Activity)?.window?.decorView as? ViewGroup

    private fun checkMainThread() {
        check(Looper.myLooper() == Looper.getMainLooper()) { "Torch must be invoked on the main thread" }
    }

    private fun addOverlay(activity: Activity, torchListener: TorchListener) {
        overlay = Overlay(activity, TorchListenerImpl(this, torchListener))
        getDecorView(activity)?.addView(overlay)
    }
}

interface TorchListener {
    fun onShow(name: String)
    fun onHide(name: String)
}

internal class TorchListenerImpl(private val torch: Torch,
                                 private val listener: TorchListener) : TorchListener {
    override fun onHide(name: String) {
        torch.hide()
        listener.onHide(name)
    }

    override fun onShow(name: String) {
        listener.onShow(name)
    }
}