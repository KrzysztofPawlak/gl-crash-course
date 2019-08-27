package com.example.gl_crash_course.model


import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("country")
    val country: String
)