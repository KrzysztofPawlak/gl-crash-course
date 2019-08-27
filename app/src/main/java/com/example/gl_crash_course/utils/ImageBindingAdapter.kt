package com.example.gl_crash_course.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.gl_crash_course.ForecastApiConst.IMG_URL_POSTFIX
import com.example.gl_crash_course.ForecastApiConst.IMG_URL_PREFIX

object ImageBindingAdapter {
    @JvmStatic
    @BindingAdapter("android:imageUrl")
    fun setImageUrl(view: ImageView, icon: String) {
        GlideApp.with(view.context).load(IMG_URL_PREFIX + icon + IMG_URL_POSTFIX).centerCrop().into(view)
    }
}