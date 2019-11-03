package com.taicho.torch

import android.app.Activity

class TorchSet(private val list: List<Torch>) {

    fun start(activity: Activity) {
        start(activity, list, 0)
    }

    private fun start(activity: Activity, sequence: List<Torch>, index: Int) {
        if (index >= sequence.size) return
        sequence[index].aim(activity, null, { start(activity, sequence, index + 1) })
    }
}