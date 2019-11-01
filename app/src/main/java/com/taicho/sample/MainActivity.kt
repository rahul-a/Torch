package com.taicho.sample

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
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
            Spotlight()
                .describe(DummyDescription(this))
                .target(Circle(getCenter(contentView), TARGET_RADIUS))
                .focus(this)
        }
    }

    private fun getCenter(view: View): Point {
        val x = view.measuredWidth.toFloat() / 2
        val y = view.measuredHeight.toFloat() / 2

        return Point(x.toInt(), y.toInt())
    }

    private class DummyDescription(private val context: Context) : Spotlight.Description() {

        override fun getView(): View {
            val view = LayoutInflater.from(context).inflate(R.layout.description_sample, null)
            view.findViewById<Button>(R.id.btnDismiss).setOnClickListener { dismiss() }
            return view
        }
    }
}