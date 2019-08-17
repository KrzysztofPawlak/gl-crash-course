package com.example.gl_crash_course.memberslist


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.gl_crash_course.service.MemberService
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MemberViewModel(application: Application) : AndroidViewModel(application) {

    private var service: MemberService = MemberService()

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val mutableMembers by lazy {
        val liveData = MutableLiveData<ArrayList<Member>>()
        getAll()
        return@lazy liveData
    }

    fun getMembers(): MutableLiveData<ArrayList<Member>> {
        return mutableMembers
    }

    private fun getAll() {
        var members: ArrayList<Member>
        scope.async {
            val results = async { service.getAllMembers() }
            members = results.await()
            mutableMembers.value = members
        }
    }
}