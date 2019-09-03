package com.example.gl_crash_course.api.model


import com.google.gson.annotations.SerializedName

data class Forecast(

    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("list")
    val list: List<City>
)