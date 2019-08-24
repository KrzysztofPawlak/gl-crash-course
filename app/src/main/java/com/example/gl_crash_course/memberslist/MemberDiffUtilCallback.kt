package com.example.gl_crash_course.memberslist

import androidx.recyclerview.widget.DiffUtil

class MemberDiffUtilCallback(private val memberList: List<Member>, private val updatedList: List<Member>) :
    DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMember = memberList[oldItemPosition]
        val updatedMember = updatedList[newItemPosition]

        return oldMember.id == updatedMember.id
    }

    override fun getOldListSize(): Int {
        return memberList.size
    }

    override fun getNewListSize(): Int {
        return updatedList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMember = memberList[oldItemPosition]
        val updatedMember = updatedList[newItemPosition]

        val isNameTheSame = oldMember.name == updatedMember.name
        val isPositionTheSame = oldMember.position == updatedMember.position
        val isUrlTheSame = oldMember.avatar == updatedMember.avatar

        return isNameTheSame && isPositionTheSame && isUrlTheSame
    }

}
