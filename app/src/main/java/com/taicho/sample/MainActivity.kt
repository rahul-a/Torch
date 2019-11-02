package com.taicho.sample

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.taicho.sample.description.DummyDescription
import com.taicho.sample.util.setFullScreenLayout
import com.taicho.spotlight.*
import com.taicho.spotlight.gravity.GRAVITY_BOTTOM
import com.taicho.spotlight.gravity.GRAVITY_END
import com.taicho.spotlight.gravity.GRAVITY_START
import com.taicho.spotlight.gravity.GRAVITY_TOP
import com.taicho.spotlight.target.CircleTarget

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFullScreenLayout(Color.parseColor("#50000000"))
        start(getSequence(), 0)
    }

    private fun getSequence(): List<Spotlight> {
        return listOf(
            GRAVITY_TOP,
            GRAVITY_BOTTOM,
            GRAVITY_END,
            GRAVITY_START
        ).map { makeSpotlight(it) }
    }

    private fun makeSpotlight(gravity: Int): Spotlight {
        return Spotlight()
            .describe(DummyDescription(this, gravity))
            .lock(CircleTarget(findViewById(R.id.btn1)))
    }

    private fun start(sequence: List<Spotlight>, index: Int) {
        if (index >= sequence.size) return
        sequence[index].aim(this, onStop = { start(sequence, index + 1) })
    }
}