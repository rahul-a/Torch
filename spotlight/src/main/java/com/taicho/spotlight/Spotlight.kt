package com.taicho.spotlight

import android.app.Activity
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.taicho.spotlight.target.Target
import java.lang.ref.WeakReference

private const val TAG = "Spotlight"

class Spotlight private constructor(view: View) {
    private val viewReference: WeakReference<View> = WeakReference(view)
    private var targets: MutableList<Target> = mutableListOf()

    fun show() {
        checkMainThread()
        check(targets.isNotEmpty()) { "Targets must not be empty" }

        if (viewReference.get() == null) {
            Log.e(TAG, "View has vanished, skipping spotlight")
        }

        viewReference.get()!!.let { view ->
            val overlay = addOverlay(view)
            for (target in targets) {
                overlay.showTarget(target)
            }
        }
    }

    fun setTarget(target: Target): Spotlight {
        targets.add(target)
        return this
    }

    private fun addOverlay(view: View): Overlay {
        return Overlay(view.context).apply {
            getDecorView(view).addView(this)
        }
    }

    private fun getDecorView(view: View): ViewGroup = (view.context as Activity).window.decorView as ViewGroup

    private fun checkMainThread() {
        check(Looper.myLooper() == Looper.getMainLooper()) { "Show must be called on the main thread" }
    }

    companion object {
        @JvmStatic
        fun on(view: View): Spotlight {
            return Spotlight(view)
        }
    }
}