package com.example.gl_crash_course

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.gl_crash_course.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding
    private lateinit var model: MemberDetalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false)

        binding.btnStartFirstFragment.setOnClickListener {
            (activity as SecondActivity).switchFragment(this)
        }

        model = ViewModelProviders.of(this).get(MemberDetalViewModel::class.java)

        val id = arguments?.getInt("id")

        if (id != null) {
            model.getOne(id)
        }

        binding.name = model.name
        binding.position = model.position
        binding.avatar = model.avatar

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SecondFragment()
    }
}
