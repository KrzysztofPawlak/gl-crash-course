package com.example.gl_crash_course

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_second.view.*

class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second, container, false)

        view.btnStartFirstFragment.setOnClickListener {
            val fragment = FirstFragment.newInstance()
            (activity as SecondActivity).replaceFragment(fragment)
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = SecondFragment()
    }
}
