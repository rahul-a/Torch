package com.taicho.torch.target.holder

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import com.taicho.torch.gravity.Gravity
import com.taicho.torch.gravity.NO_GRAVITY

abstract class TargetHolder(private val gravity: Int = NO_GRAVITY) {
    internal lateinit var dismiss: (() -> Unit)

    abstract fun create(inflater: LayoutInflater, root: ViewGroup): View

    @CallSuper
    open fun onBind(view: View, targetRect: Rect) {
        val gravity = Gravity.create(view, gravity)
        gravity.apply(view, targetRect)
    }

    fun dismiss() {
        dismiss.invoke()
    }
}