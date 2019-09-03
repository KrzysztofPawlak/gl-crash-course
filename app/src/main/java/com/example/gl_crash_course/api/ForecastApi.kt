package com.example.gl_crash_course.api

import com.example.gl_crash_course.api.model.City
import com.example.gl_crash_course.api.model.Forecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {

    @GET("weather?")
    fun getWeatherById(
        @Query("id") cnt: String, @Query("appid") appid: String, @Query("units") units: String
    ): Call<City>

    @GET("group?")
    fun getSetOfWeatherByIds(
        @Query("id") cnt: String, @Query("appid") appid: String, @Query("units") units: String
    ): Call<Forecast>
}