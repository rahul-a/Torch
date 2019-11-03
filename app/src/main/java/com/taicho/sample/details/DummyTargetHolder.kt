package com.taicho.sample.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.taicho.sample.R
import com.taicho.torch.target.holder.TargetHolder

class DummyTargetHolder(gravity: Int) : TargetHolder(gravity) {

    override fun create(inflater: LayoutInflater, root: ViewGroup): View {
        val view = inflater.inflate(R.layout.details_sample, root, false)
        val dismissBtn = view.findViewById<TextView>(R.id.title)

        dismissBtn.setOnClickListener { dismiss() }
        return view
    }
}
