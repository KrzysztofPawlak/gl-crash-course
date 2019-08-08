package com.example.gl_crash_course

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_first.view.*

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_first, container, false)

        view.btnStartSecondFragment.setOnClickListener {
            val fragment = SecondFragment.newInstance()
            (activity as SecondActivity).replaceFragment(fragment)
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = FirstFragment()
    }
}
