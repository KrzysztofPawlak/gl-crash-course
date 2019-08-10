package com.example.gl_crash_course

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_first.view.*

class FirstFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_first, container, false)

        view.btnStartSecondFragment.setOnClickListener {
            val fragment = SecondFragment.newInstance()
            (activity as SecondActivity).replaceFragment(fragment, getString(R.string.tag_fragment_second))
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        (activity as SecondActivity).setTransitionCounter()
    }

    interface OnFragmentInteractionListener {
        fun setTransitionCounter()
    }

    fun updateTransitionCounter(amount: Int) {
        view?.first_fragment_text_transition?.text = getString(R.string.fragment_first_text_transition) + amount
    }

    companion object {
        @JvmStatic
        fun newInstance() = FirstFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
