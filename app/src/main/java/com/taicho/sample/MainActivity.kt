package com.taicho.sample

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taicho.sample.description.DummyDescription
import com.taicho.sample.util.setFullScreenLayout
import com.taicho.spotlight.*
import com.taicho.spotlight.target.CircleTarget
import com.taicho.spotlight.target.RectTarget
import com.taicho.spotlight.target.RoundRectTarget

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setFullScreenLayout(Color.parseColor("#50000000"))

        Spotlight()
            .describe(DummyDescription(this, GRAVITY_END))
            .lock(CircleTarget(findViewById(R.id.btn1)))
            .aim(this)
    }
}