package com.taicho.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.taicho.sample.details.DummyDetails
import com.taicho.sample.util.setFullScreenLayout
import com.taicho.torch.Torch
import com.taicho.torch.TorchSet
import com.taicho.torch.gravity.GRAVITY_BOTTOM
import com.taicho.torch.gravity.GRAVITY_END
import com.taicho.torch.gravity.GRAVITY_START
import com.taicho.torch.gravity.GRAVITY_TOP
import com.taicho.torch.target.RoundRectTarget

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFullScreenLayout(ContextCompat.getColor(this, R.color.colorPrimaryDark))

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

        result.addAll(gravityList.mapIndexed { i, gravity ->
            flashTorch(gravity, R.id.btn1, "Foo-${i + 1}")
        })

        result.addAll(gravityList.mapIndexed { i, gravity ->
            flashTorch(gravity, R.id.btn2, "Bar-${i + 1}")
        })

        return result
    }

    private fun flashTorch(gravity: Int, resId: Int, name: String): Torch {
        val details = DummyDetails(gravity)
        val target = RoundRectTarget(name, findViewById(resId), details, 16F)
        return Torch(target)
    }
}