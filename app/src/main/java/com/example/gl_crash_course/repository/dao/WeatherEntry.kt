package com.example.gl_crash_course.repository.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "pogoda_table")
data class WeatherEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val ipi_id: Int,
    val name: String,
    val temperature: String,
    val icon: String,
    val refreshed: LocalDateTime
)