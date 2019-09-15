package com.example.gl_crash_course.settings.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.gl_crash_course.R

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val languagesList = application.resources.getStringArray(R.array.languages)
    val languageIdItemPosition = MutableLiveData<Int>()
    var languageIdValue
        get() = languageIdItemPosition.value?.let {
            languagesList?.get(it)
        }
        set(value) {
            val position = languagesList?.indexOfFirst {
                it == value
            } ?: -1
            if (position != -1) {
                languageIdItemPosition.value = position
            }
        }

    var intervalHours = MutableLiveData<Int>()
    var intervalMinutes = MutableLiveData<Int>()
}