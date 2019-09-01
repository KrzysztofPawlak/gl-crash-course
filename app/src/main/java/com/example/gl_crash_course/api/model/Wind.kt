package com.example.gl_crash_course.api.model


import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("deg")
    val deg: Int,
    @SerializedName("gust")
    val gust: Int,
    @SerializedName("speed")
    val speed: Double
)