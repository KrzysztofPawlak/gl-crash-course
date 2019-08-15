package com.example.gl_crash_course.memberslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gl_crash_course.R
import com.example.gl_crash_course.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private lateinit var model: MemberViewModel
    private lateinit var binding: FragmentFirstBinding
    private val adapter = MemberAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.listRecyclerView.adapter = adapter
        binding.listRecyclerView.layoutManager = layoutManager

        model = ViewModelProviders.of(this).get(MemberViewModel::class.java)

        model.getMembers().observe(viewLifecycleOwner, Observer<List<Member>> { memberList ->
            adapter.updateMembers(memberList)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = FirstFragment()
    }

}
