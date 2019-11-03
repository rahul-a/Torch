package com.taicho.torch

import android.app.Activity
import android.content.Context
import android.os.Looper
import android.view.ViewGroup
import com.taicho.torch.target.ViewTarget

class Torch constructor(private val target: ViewTarget): Listener {

    private lateinit var overlay: Overlay
    private var listener: Listener? = null

    fun beam(activity: Activity, listener: Listener) {
        checkMainThread()
        this.listener = listener

        addOverlay(activity)
        render()
    }

    private fun render() {
        if (!::overlay.isInitialized) return

        overlay.post {
            overlay.render(target)
        }
    }

    override fun onHide(name: String) {
        if (!::overlay.isInitialized) return

        overlay.let {
            if (it.isAttachedToWindow) {
                (it.parent as ViewGroup).removeView(it)
            }
        }
        listener?.onHide(name)
    }

    override fun onShow(name: String) {
        listener?.onShow(name)
    }

    private fun getDecorView(context: Context): ViewGroup? =
        (context as? Activity)?.window?.decorView as? ViewGroup

    private fun checkMainThread() {
        check(Looper.myLooper() == Looper.getMainLooper()) { "Torch must be invoked on the main thread" }
    }

    private fun addOverlay(activity: Activity) {
        overlay = Overlay(activity, this)
        getDecorView(activity)?.addView(overlay)
    }
}

interface Listener {
    fun onShow(name: String)
    fun onHide(name: String)
}
