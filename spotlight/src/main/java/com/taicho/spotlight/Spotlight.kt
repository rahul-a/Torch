package com.taicho.spotlight

import android.app.Activity
import android.content.Context
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import com.taicho.spotlight.gravity.NO_GRAVITY
import com.taicho.spotlight.target.Target

class Spotlight constructor(private val name: String? = null) {

    private var overlay: Overlay? = null
    private var description: Description? = null
    private val targets: MutableList<Target> = mutableListOf()

    fun aim(activity: Activity, onStart: Consumer? = null, onStop: Consumer? = null) {
        checkMainThread()
        overlay = overlay(activity)
        overlay?.post {
            render(onStart, onStop)
        }
    }

    fun lock(target: Target): Spotlight {
        targets.add(target)
        return this
    }

    fun describe(description: Description): Spotlight {
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

    private fun overlay(context: Context): Overlay {
        return Overlay(context).apply {
            getDecorView(context).addView(this)
        }
    }

    private fun getDecorView(context: Context): ViewGroup =
        (context as Activity).window.decorView as ViewGroup

    private fun checkMainThread() {
        check(Looper.myLooper() == Looper.getMainLooper()) { "Spotlight must be invoked on the main thread" }
    }

    private fun render(onStart: Consumer?, onStop: Consumer?) {
        renderTargets()

        description?.let {
            it.dismiss = {
                onStop?.invoke(name)
                hide()
            }

            overlay?.addDescription(it)
        }

        onStart?.invoke(name)
    }

    private fun renderTargets() {
        for (target in targets) {
            overlay?.add(target)
        }
    }

    abstract class Description(internal val gravity: Int = NO_GRAVITY) {
        internal lateinit var dismiss: (() -> Unit)

        abstract fun getView(root: ViewGroup): View

        fun dismiss() {
            dismiss.invoke()
        }
    }

}

typealias Consumer = (String?) -> Unit
