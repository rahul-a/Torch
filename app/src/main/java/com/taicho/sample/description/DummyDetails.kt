package com.taicho.sample.description

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.taicho.sample.R
import com.taicho.torch.target.ViewTarget

class DummyDetails(gravity: Int) : ViewTarget.Details(gravity) {

    override fun getView(inflater: LayoutInflater, root: ViewGroup): View {
        val view = inflater.inflate(R.layout.description_sample, root, false)
        val dismissBtn = view.findViewById<TextView>(R.id.title)

        dismissBtn.setOnClickListener { dismiss() }
        return view
    }

}
