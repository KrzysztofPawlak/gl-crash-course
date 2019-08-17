package com.example.gl_crash_course.memberslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gl_crash_course.databinding.ListItemBinding

class MemberAdapter : RecyclerView.Adapter<MemberAdapter.ViewHolder>() {

    private var memberList: List<Member> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return memberList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position < itemCount) {
            holder.bind(holder.binding, memberList[position])
            holder.binding.executePendingBindings()
        }
    }

    class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(binding: ViewDataBinding, member: Member) {
            when (binding) {
                is ListItemBinding -> {
                    binding.name = member.name
                    binding.position = member.position
                    binding.url = member.url
                }
            }
        }
    }

    fun updateMembers(updatedList: List<Member>) {
        val result = DiffUtil.calculateDiff(MemberDiffUtilCallback(memberList, updatedList))
        memberList = updatedList.toMutableList()
        result.dispatchUpdatesTo(this)
    }
}