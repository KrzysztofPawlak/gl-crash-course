package com.example.gl_crash_course.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cityEntry: CityEntry)

    @Query("SELECT * FROM city_table")
    fun getAll(): LiveData<List<CityEntry>>

    @Delete
    fun delete(cityEntry: CityEntry)
}