package com.taicho.spotlight

import android.app.Activity
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import com.taicho.spotlight.target.Target
import java.lang.ref.WeakReference

private const val TAG = "Spotlight"

class Spotlight private constructor(view: View) {
    private val viewReference: WeakReference<View> = WeakReference(view)
    private var targets: MutableList<Target> = mutableListOf()
    private var overlay: Overlay? = null

    fun show() {
        validate()

        viewReference.get()?.let { view ->
            overlay = addOverlay(view)

            for (target in targets) {
                overlay!!.showTarget(target)
            }
        }
    }

    fun setTarget(target: Target): Spotlight {
        targets.add(target)
        return this
    }

    fun hide() {
        overlay?.let {
            if (it.isAttachedToWindow) {
                (it.parent as ViewGroup).removeView(it)
            }
        }
    }

    private fun addOverlay(view: View): Overlay {
        return Overlay(view.context).apply {
            getDecorView(view).addView(this)
        }
    }

    private fun getDecorView(view: View): ViewGroup = (view.context as Activity).window.decorView as ViewGroup

    private fun validate() {
        check(Looper.myLooper() == Looper.getMainLooper()) { "Show must be called on the main thread" }
        check(targets.isNotEmpty()) { "Targets must not be empty" }
    }

    companion object {
        @JvmStatic
        fun on(view: View): Spotlight {
            return Spotlight(view)
        }
    }
}