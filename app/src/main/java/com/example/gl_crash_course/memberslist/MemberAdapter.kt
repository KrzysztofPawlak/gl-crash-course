package com.example.gl_crash_course.memberslist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gl_crash_course.databinding.ListItemBinding
import kotlinx.android.synthetic.main.list_item.view.*

class MemberAdapter(private val callback: OnMemberClickListener, private val callbackVisited: VisitedInterface) :
    RecyclerView.Adapter<MemberAdapter.ViewHolder>() {

    private var memberList: List<Member> = ArrayList()

    interface OnMemberClickListener {
        fun onMemberClick(id: Int)
    }

    interface VisitedInterface {
        fun isVisited(id: Int): Boolean
    }

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
            holder.bind(holder.binding, memberList[position], callback)
            holder.binding.executePendingBindings()

            if (callbackVisited.isVisited(memberList[position].id)) {
                holder.itemView.list_item.setBackgroundColor(Color.GRAY)
            }
        }
    }

    class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(binding: ViewDataBinding, member: Member, callback: OnMemberClickListener) {
            when (binding) {
                is ListItemBinding -> {
                    binding.id = member.id
                    binding.name = member.name
                    binding.position = member.position
                    binding.avatar = member.avatar
                    binding.listener = callback
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