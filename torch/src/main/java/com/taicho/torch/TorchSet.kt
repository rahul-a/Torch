package com.taicho.torch

import android.app.Activity
import android.util.Log

class TorchSet(private val list: List<Torch>) {

    private var index = 0

    fun start(activity: Activity) {
        start(activity, Stepper(activity))
    }

    private fun start(activity: Activity, stepper: Stepper) {
        if (index >= list.size) return
        list[index++].beam(activity, stepper)
    }

    private inner class Stepper(private val activity: Activity) : TorchListener {
        override fun onHide(name: String) {
            Log.i("Stepper", "OnHide: $name")
            start(activity, this)
        }

        override fun onShow(name: String) {
            Log.i("Stepper", "OnShow: $name")
        }
    }
}