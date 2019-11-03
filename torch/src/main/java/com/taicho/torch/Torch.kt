package com.taicho.torch

import android.app.Activity
import android.content.Context
import android.graphics.RectF
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.taicho.torch.gravity.NO_GRAVITY
import com.taicho.torch.target.Target

class Torch constructor(private val name: String? = null) {

    private var overlay: Overlay? = null
    private var description: Description? = null
    private val targets: MutableList<Target> = mutableListOf()

    fun aim(activity: Activity, onStart: Consumer? = null, onStop: Consumer? = null) {
        checkMainThread()
        overlay = Overlay(activity).apply {
            addOverlay(activity, this)
            renderSpotlight(this, onStart, onStop)
        }
    }

    fun lock(target: Target): Torch {
        targets.add(target)
        return this
    }

    fun describe(description: Description): Torch {
        this.description = description
        return this
    }

    private fun hide() {
        detach()
        reset()
    }

    private fun detach() {
        overlay?.let {
            if (it.isAttachedToWindow) {
                (it.parent as ViewGroup).removeView(it)
            }
        }

        overlay = null
    }

    private fun reset() {
        description = null
        targets.clear()
    }

    private fun getDecorView(context: Context): ViewGroup =
        (context as Activity).window.decorView as ViewGroup

    private fun checkMainThread() {
        check(Looper.myLooper() == Looper.getMainLooper()) { "Torch must be invoked on the main thread" }
    }

    private fun renderTargets(overlay: Overlay) {
        for (target in targets) {
            overlay.add(target)
        }
    }

    private fun renderSpotlight(overlay: Overlay, onStart: Consumer?, onStop: Consumer?): Boolean {
        description?.dismiss = {
            onStop?.invoke(name)
            hide()
        }

        return overlay.post {
            renderTargets(overlay)
            renderDescription(overlay)
            onStart?.invoke(name)
        }
    }

    private fun renderDescription(overlay: Overlay) {
        description?.let {
            overlay.addDescription(it)
        }
    }

    private fun addOverlay(activity: Activity, it: Overlay) {
        getDecorView(activity).addView(it)
    }

    abstract class Description(internal val gravity: Int = NO_GRAVITY) {
        internal lateinit var dismiss: (() -> Unit)

        abstract fun getView(root: ViewGroup): View
        open fun onViewCreated(view: View, target: RectF) {
            Log.i("Description", "OnViewCreated: $target")
        }

        fun dismiss() {
            dismiss.invoke()
        }
    }

}

typealias Consumer = (String?) -> Unit
