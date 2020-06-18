package com.scchao.wtrstkpractice.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.scchao.wtrstkpractice.R

class DetailFragment : Fragment() {
    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inputStr = arguments?.getString("detail_prop")
        System.out.println("Get input prop ${inputStr}")
        val root = inflater.inflate(R.layout.fragment_detail, container, false)
        root.findViewById<TextView>(R.id.title_text).text = inputStr

        return root
    }
}