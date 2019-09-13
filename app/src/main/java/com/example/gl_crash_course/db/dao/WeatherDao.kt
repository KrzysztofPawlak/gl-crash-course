package com.example.gl_crash_course.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gl_crash_course.db.model.WeatherEntry
import java.time.LocalDateTime

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherEntry: WeatherEntry)

    @Query("SELECT * FROM pogoda_table")
    fun getAll(): LiveData<List<WeatherEntry>>

    @Query("UPDATE pogoda_table SET temperature = :temperature, icon = :icon, refreshed = :refreshed WHERE api_id = :api_id")
    fun update(temperature: String, icon: String, refreshed: LocalDateTime, api_id: Int)

    @Query("DELETE FROM pogoda_table WHERE api_id = :api_id")
    fun delete(api_id: Int)

}