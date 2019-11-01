package com.taicho.sample

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.taicho.spotlight.Spotlight
import com.taicho.spotlight.target.Circle

private const val TARGET_RADIUS = 100F

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        Spotlight()
            .describe(DummyDescription(this))
            .target(Circle(findViewById(R.id.btn1), TARGET_RADIUS))
            .focus(this)
    }

    private class DummyDescription(private val context: Context) : Spotlight.Description() {

        @SuppressLint("InflateParams")
        override fun getView(): View {
            val view = LayoutInflater.from(context).inflate(R.layout.description_sample, null)
            view.findViewById<Button>(R.id.btnDismiss).setOnClickListener { dismiss() }
            return view
        }
    }
}