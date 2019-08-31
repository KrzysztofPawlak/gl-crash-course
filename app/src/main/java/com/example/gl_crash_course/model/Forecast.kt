package com.example.gl_crash_course.model


import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("cod")
    val cod: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("list")
    val list: List<City>,
    @SerializedName("message")
    val message: String
)