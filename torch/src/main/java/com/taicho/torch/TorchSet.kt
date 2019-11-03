package com.taicho.torch

import android.app.Activity
import android.util.Log

class TorchSet(private val list: List<Torch>) {

    fun start(activity: Activity) {
        start(activity, list, 0)
    }

    private fun start(activity: Activity, sequence: List<Torch>, index: Int) {
        if (index >= sequence.size) return
        sequence[index].beam(activity, object : Listener {
            override fun onHide(name: String) {
                Log.i("TorchSet", "OnHide: $name")
                start(activity, sequence, index + 1)
            }

            override fun onShow(name: String) {
                Log.i("TorchSet", "OnShow: $name")
            }
        })
    }
}