package com.example.gl_crash_course.api.model


import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("country")
    val country: String,
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int,
    @SerializedName("timezone")
    val timezone: Int

)