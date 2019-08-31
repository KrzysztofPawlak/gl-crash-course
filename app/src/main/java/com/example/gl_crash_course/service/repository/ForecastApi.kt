package com.example.gl_crash_course.service.repository

import com.example.gl_crash_course.model.City
import com.example.gl_crash_course.model.Forecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {

    @GET("find?")
    fun findCitiesAround(
        @Query("lat") lat: String, @Query("lon") lon: String, @Query("cnt") cnt: String,
        @Query("appid") appid: String, @Query("units") units: String
    ): Call<Forecast>

    @GET("weather?")
    fun getWeatherById(
        @Query("id") cnt: String, @Query("appid") appid: String, @Query("units") units: String
    ): Call<City>
}