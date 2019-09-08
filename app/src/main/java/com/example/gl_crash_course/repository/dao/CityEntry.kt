package com.example.gl_crash_course.repository.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_table")
data class CityEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val api_id: Int,
    val name: String,
    val country: String
)