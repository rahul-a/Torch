package com.taicho.sample

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taicho.sample.description.DummyDetails
import com.taicho.sample.util.setFullScreenLayout
import com.taicho.torch.*
import com.taicho.torch.gravity.GRAVITY_BOTTOM
import com.taicho.torch.gravity.GRAVITY_END
import com.taicho.torch.gravity.GRAVITY_START
import com.taicho.torch.gravity.GRAVITY_TOP
import com.taicho.torch.target.CircleTarget
import com.taicho.torch.target.RoundRectTarget

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFullScreenLayout(Color.parseColor("#50000000"))

        TorchSet(getSequence()).start(this)
    }

    private fun getSequence(): List<Torch> {
        val result = mutableListOf<Torch>()
        val gravityList = listOf(
            GRAVITY_TOP,
            GRAVITY_BOTTOM,
            GRAVITY_END,
            GRAVITY_START
        )

        result.addAll(gravityList.mapIndexed { i, gravity -> makeSpotlight(gravity, R.id.btn1, "Foo-${i + 1}") })
        result.addAll(gravityList.mapIndexed { i, gravity -> makeSpotlight(gravity, R.id.btn2, "Bar-${i + 1}") })

        return result
    }

    private fun makeSpotlight(gravity: Int, resId: Int, name: String): Torch {
        return Torch(RoundRectTarget(name, findViewById(resId), DummyDetails(gravity), 16F))
    }
}