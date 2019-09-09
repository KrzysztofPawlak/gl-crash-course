package com.example.gl_crash_course.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gl_crash_course.db.model.CityEntry

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cityEntry: CityEntry)

    @Query("SELECT * FROM city_table")
    fun getAll(): LiveData<List<CityEntry>>

    @Query("DELETE FROM city_table WHERE api_id = :api_id")
    fun delete(api_id: Int)
}