package com.example.gl_crash_course


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.gl_crash_course.service.MemberService

class MemberDetalViewModel(application: Application) : AndroidViewModel(application) {

    private var service: MemberService = MemberService(application)

    lateinit var name: String
    lateinit var position: String
    lateinit var avatar: String

    fun getOne(id: Int) {
        val member = service.getOne(id)
        name = member.name
        position = member.position
        avatar = member.avatar
    }
}