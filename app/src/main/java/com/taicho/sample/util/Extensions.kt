package com.taicho.sample.util

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager

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

private fun onAtLeastApi(api: Int, block: () -> Unit) {
    if (Build.VERSION.SDK_INT >= api) block()
}