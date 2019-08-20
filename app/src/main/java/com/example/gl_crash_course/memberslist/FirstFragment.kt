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
import com.example.gl_crash_course.SecondActivity
import com.example.gl_crash_course.SecondFragment
import com.example.gl_crash_course.databinding.FragmentFirstBinding

class FirstFragment : Fragment(), MemberAdapter.OnMemberClickListener, MemberAdapter.VisitedInterface {

    override fun isVisited(id: Int): Boolean {
        return (activity as SecondActivity).model.isVisited(id)
    }

    override fun onMemberClick(id: Int) {
        (activity as SecondActivity).model.markAsVisited(id)
        val bundle = Bundle()
        bundle.putInt("id", id)
        val fragment = SecondFragment.newInstance()
        fragment.arguments = bundle
        (activity as SecondActivity).replaceFragment(fragment, getString(R.string.tag_fragment_first))
    }

    private lateinit var model: MemberViewModel
    private lateinit var binding: FragmentFirstBinding
    private val adapter = MemberAdapter(this, this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)

        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.listRecyclerView.adapter = adapter
        binding.listRecyclerView.layoutManager = layoutManager

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
