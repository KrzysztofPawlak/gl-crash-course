package com.example.gl_crash_course.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class VisitedViewModel(application: Application) : AndroidViewModel(application) {

    private val map: HashMap<Int, String> = hashMapOf()

    fun isVisited(position: Int): Boolean {
        return map.containsKey(position)
    }

    fun markAsVisited(position: Int) {
        map[position] = "visited"
    }
}