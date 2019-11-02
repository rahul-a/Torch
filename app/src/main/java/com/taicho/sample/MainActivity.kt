package com.taicho.sample

import android.graphics.Color
import android.os.Bundle
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

        SpotlightSet(getSequence()).start(this)
    }

    private fun getSequence(): List<Spotlight> {
        val result = mutableListOf<Spotlight>()
        val gravityList = listOf(
            GRAVITY_TOP,
            GRAVITY_BOTTOM,
            GRAVITY_END,
            GRAVITY_START
        )

        result.addAll(gravityList.map { makeSpotlight(it, R.id.btn1) })
        result.addAll(gravityList.map { makeSpotlight(it, R.id.btn2) })

        return result
    }

    private fun makeSpotlight(gravity: Int, resId: Int): Spotlight {
        return Spotlight()
            .describe(DummyDescription(this, gravity))
            .lock(CircleTarget(findViewById(resId)))
    }
}