package com.taicho.spotlight

import android.app.Activity
import android.content.Context
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import com.taicho.spotlight.target.Target

private const val TAG = "Spotlight"

class Spotlight {

    private var overlay: Overlay? = null
    private var description: Description? = null
    private val targets: MutableList<Target> = mutableListOf()

    fun focus(activity: Activity) {
        checkMainThread()

        addOverlay(activity).apply {
            this@Spotlight.overlay = this

            for (target in targets) {
                add(target)
            }

            description?.let {
                addView(it.getView())
            }
        }
    }

    fun addTarget(target: Target): Spotlight {
        targets.add(target)
        return this
    }

    fun describe(description: Description): Spotlight {
        description.dismiss = { hide() }
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

    private fun addOverlay(context: Context): Overlay {
        return Overlay(context).apply {
            getDecorView(context).addView(this)
        }
    }

    private fun getDecorView(context: Context): ViewGroup = (context as Activity).window.decorView as ViewGroup

    private fun checkMainThread() {
        check(Looper.myLooper() == Looper.getMainLooper()) { "Spotlight must be invoked on the main thread" }
    }

    abstract class Description {
        internal lateinit var dismiss: (() -> Unit)

        abstract fun getView(): View

        fun dismiss() {
            dismiss.invoke()
        }
    }
}