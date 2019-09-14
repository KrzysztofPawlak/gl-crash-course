package com.example.gl_crash_course.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gl_crash_course.db.model.WeatherEntry
import java.time.LocalDateTime

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherEntry: WeatherEntry)

    @Query("SELECT * FROM weather_city_table")
    fun getAll(): LiveData<List<WeatherEntry>>

    @Query("UPDATE weather_city_table SET temperature = :temperature, icon = :icon, refreshed = :refreshed, " +
            "description = :description, pressure = :pressure, humidity = :humidity, wind_speed = :wind_speed, " +
            "sunrise = :sunrise, sunset = :sunset WHERE api_id = :api_id")
    fun update(temperature: String, icon: String, refreshed: LocalDateTime, api_id: Int, description: String,
               pressure: Double, humidity: Int, wind_speed: Double, sunrise: Int, sunset: Int)

    @Query("DELETE FROM weather_city_table WHERE api_id = :api_id")
    fun delete(api_id: Int)

    @Query("SELECT * FROM weather_city_table WHERE api_id = :api_id")
    fun get(api_id: Int): LiveData<WeatherEntry>

}