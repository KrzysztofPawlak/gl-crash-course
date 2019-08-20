package com.example.gl_crash_course.service

import com.example.gl_crash_course.memberslist.Member
import kotlinx.coroutines.delay

class MemberService {

    private fun getArrayOfMembers(): ArrayList<Member> {
        val member1 = Member(1, "foo", "bar", "baz")
        val member2 = Member(2, "bar", "baz", "foo")
        val member3 = Member(3, "baz", "foo", "bar")

        return arrayListOf(member1, member2, member3)
    }

    // TODO: read from json
    suspend fun getAllMembers(): ArrayList<Member> {
        val delayMillis: Long = 2000

        delay(delayMillis)

        return getArrayOfMembers()
    }

    fun getOne(idToFind: Int): Member {
        return getArrayOfMembers().single { member -> member.id == idToFind }
    }
}