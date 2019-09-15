package com.example.gl_crash_course

import android.content.Context
import android.content.res.Configuration
import java.util.*

class LocaleUtils {

    constructor(context: Context, lang: String) {
        setLocale(context, lang)
    }

    private fun setLocale(context: Context, lang: String) {
        var locale = Locale(lang)
        var configuration = Configuration()
        configuration.setLocale(locale)
        var resource = context.resources
        resource.updateConfiguration(configuration, resource.displayMetrics)
    }
}