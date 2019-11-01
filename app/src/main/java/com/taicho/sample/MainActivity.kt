package com.taicho.sample

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
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
            Spotlight
                .on(contentView)
                .setTarget(Circle(getCenter(contentView), TARGET_RADIUS))
                .addDescription(DummyDescription(this))
                .show()
        }
    }

    private fun Spotlight.Description.getDescriptionView(): View {
        return Button(this@MainActivity).apply {
            text = "Dismiss"
            layoutParams = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            translationY = 200F
            setOnClickListener { dismiss() }
        }
    }

    private fun getCenter(view: View): Point {
        val x = view.measuredWidth.toFloat() / 2
        val y = view.measuredHeight.toFloat() / 2

        return Point(x.toInt(), y.toInt())
    }

    private class DummyDescription(private val context: Context) : Spotlight.Description() {

        override fun getView(): View {
            return getButton("Dismiss")
        }

        private fun getButton(displayText: String): View {
            return Button(context).apply {
                text = displayText
                layoutParams = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                translationY = 200F
                setOnClickListener { dismiss() }
            }
        }
    }
}