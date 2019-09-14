package com.example.gl_crash_course.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "weather_city_table")
data class WeatherEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val api_id: Int,
    val name: String,
    val temperature: String,
    val icon: String,
    val refreshed: LocalDateTime,
    val description: String,
    val pressure: Double,
    val humidity: Int,
    val wind_speed: Double,
    val sunrise: Int,
    val sunset: Int
)