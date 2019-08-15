package com.example.gl_crash_course.memberslist

import android.app.Application
import android.os.Handler
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MemberViewModel(application: Application) : AndroidViewModel(application) {

    private var mutableMembers: MutableLiveData<ArrayList<Member>> = MutableLiveData()

    init {
        mutableMembers.value = arrayListOf()
        getAll()
        append()
    }

    private fun updateAll(membersList: ArrayList<Member>) {
        mutableMembers.value = membersList
    }

    fun getMembers(): MutableLiveData<ArrayList<Member>> {
        return mutableMembers
    }

    fun getAll() {
        val delayMillis: Long = 2000
        Handler().postDelayed({
            val member1 = Member(1, "foo", "bar", "baz")
            val member3 = Member(3, "foo", "bar", "baz")
            val member2 = Member(2, "baz", "bar", "foo")
            this.updateAll(arrayListOf(member1, member2, member3))
        }, delayMillis)
    }

    fun append() {
        val delayMillis: Long = 5000
        Handler().postDelayed({
            val member3 = Member(5, "foo", "bar", "baz")
            val member4 = Member(6, "baz", "bar", "foo")
            mutableMembers.value?.addAll((arrayListOf(member3, member4)))
            mutableMembers.notifyObserver()
        }, delayMillis)
    }

    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
}