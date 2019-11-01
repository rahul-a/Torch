package com.taicho.sample

import android.graphics.Point
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.taicho.spotlight.Spotlight
import com.taicho.spotlight.target.Circle

private const val TARGET_RADIUS = 100F

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contentView = FrameLayout(this)
        setContentView(contentView)

        contentView.post {
            val spotlight = Spotlight
                .on(contentView)
                .setTarget(Circle(getCenter(contentView), TARGET_RADIUS))

            spotlight.show()
        }
    }

    private fun getCenter(view: View): Point {
        val x = view.measuredWidth.toFloat() / 2
        val y = view.measuredHeight.toFloat() / 2

        return Point(x.toInt(), y.toInt())
    }
}