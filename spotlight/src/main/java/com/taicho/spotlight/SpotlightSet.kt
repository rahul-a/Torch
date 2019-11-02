package com.taicho.spotlight

import android.app.Activity

class SpotlightSet(private val list: List<Spotlight>) {

    fun start(activity: Activity) {
        start(activity, list, 0)
    }

    private fun start(activity: Activity, sequence: List<Spotlight>, index: Int) {
        if (index >= sequence.size) return
        sequence[index].aim(activity, null, { start(activity, sequence, index + 1) })
    }
}