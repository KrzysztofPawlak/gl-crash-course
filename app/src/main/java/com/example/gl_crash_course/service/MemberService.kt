package com.example.gl_crash_course.service

import com.example.gl_crash_course.memberslist.Member
import kotlinx.coroutines.delay

class MemberService {

    // TODO: read from json
    suspend fun getAllMembers(): ArrayList<Member> {
        val member1 = Member(1, "foo", "bar", "baz")
        val member3 = Member(3, "foo", "bar", "baz")
        val member2 = Member(2, "baz", "bar", "foo")

        val delayMillis: Long = 2000

        delay(delayMillis)

        return arrayListOf(member1, member2, member3)
    }
}