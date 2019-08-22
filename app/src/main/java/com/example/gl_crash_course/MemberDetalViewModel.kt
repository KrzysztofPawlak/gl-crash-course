package com.example.gl_crash_course


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.gl_crash_course.service.MemberService

class MemberDetalViewModel(application: Application) : AndroidViewModel(application) {

    private var service: MemberService = MemberService(application)

    lateinit var name: String
    lateinit var position: String
    lateinit var url: String

    fun getOne(id: Int) {
        val member = service.getOne(id)
        name = member.name
        position = member.position
        url = member.url
    }
}