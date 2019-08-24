package com.example.gl_crash_course.memberslist


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.gl_crash_course.service.MemberService
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MemberViewModel(application: Application) : AndroidViewModel(application) {

    private var service: MemberService = MemberService(application)

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    private val limit = 10
    private var currentOffset = 0
    var refresh = MutableLiveData<Boolean>()

    private val mutableMembers by lazy {
        val liveData = MutableLiveData<ArrayList<Member>>()
        getSubset()
        return@lazy liveData
    }

    fun getMembers(): MutableLiveData<ArrayList<Member>> {
        return mutableMembers
    }

    fun incrementOffset() {
        currentOffset += 1
    }

    fun getSubset() {
        var members: ArrayList<Member>
        scope.async {
            val results = async { service.getSubset(currentOffset, limit) }
            members = results.await()
            mutableMembers += members
        }.invokeOnCompletion { refresh.value = false }
    }

    operator fun <T> MutableLiveData<ArrayList<T>>.plusAssign(values: List<T>) {
        val value = this.value ?: arrayListOf()
        value.addAll(values)
        this.value = value
    }
}