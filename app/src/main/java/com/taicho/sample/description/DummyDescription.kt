package com.taicho.sample.description

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.taicho.sample.R
import com.taicho.spotlight.Spotlight

class DummyDescription(private val context: Context, gravity: Int) : Spotlight.Description(gravity) {

    @SuppressLint("InflateParams")
    override fun getView(root: ViewGroup): View {
        val view =
            LayoutInflater
                .from(context)
                .inflate(R.layout.description_sample, root, false)

        addListener(view)
        return view
    }

    private fun addListener(view: View) {
        val dismissBtn = view.findViewById<TextView>(R.id.title)
        dismissBtn.setOnClickListener { dismiss() }
    }
}
