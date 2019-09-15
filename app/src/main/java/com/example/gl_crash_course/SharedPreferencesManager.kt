package com.example.gl_crash_course

import android.content.Context
import android.content.SharedPreferences
import com.example.gl_crash_course.WeatherApiConst.LANGUAGES

class SharedPreferencesManager(val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("pref", Context.MODE_PRIVATE)

    fun save(KEY_NAME: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_NAME, value)
        editor.apply()
    }


    fun getValueString(KEY_NAME: String): String? {
        return sharedPreferences.getString(KEY_NAME, "English")
    }

    fun setLanguage() {
        val lang = getValueString("lang")
        if (lang != null) {
            LocaleUtils(context, LANGUAGES.getValue(lang))
        }
    }
}