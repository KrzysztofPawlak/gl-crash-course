package com.example.gl_crash_course.memberslist

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ImageBindingAdapter {
    @JvmStatic
    @BindingAdapter("android:imageUrl")
    fun setImageUrl(view: ImageView, avatar: String) {
        Glide.with(view.context).load(avatar).centerCrop().into(view)
    }
}