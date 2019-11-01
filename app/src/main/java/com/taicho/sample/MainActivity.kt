package com.taicho.sample

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.taicho.spotlight.Spotlight
import com.taicho.spotlight.target.CircleTarget
import com.taicho.spotlight.target.RoundRectTarget

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setFullScreenLayout(Color.parseColor("#50000000"))

        Spotlight()
            .describe(DummyDescription(this))
            .addTarget(CircleTarget(findViewById(R.id.btn1)))
            .addTarget(RoundRectTarget(findViewById(R.id.btn2), 16F))
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

@SuppressLint("NewApi")
fun Activity.setFullScreenLayout(statusBarColor: Int) {
    onAtLeastApi(Build.VERSION_CODES.KITKAT) {
        window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        onAtLeastApi(Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = statusBarColor
        }
    }
}

fun onAtLeastApi(api: Int, block: () -> Unit) {
    if(Build.VERSION.SDK_INT >= api) block()
}