package com.example.gl_crash_course.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.gl_crash_course.db.model.SearchHistoryEntry

@Dao
interface SearchHistoryDao {
    @Insert
    fun insert(searchHistoryEntry: SearchHistoryEntry)

    @Query("SELECT * FROM search_history_table ORDER BY searchDate DESC LIMIT 3")
    fun getLast3(): LiveData<List<SearchHistoryEntry>>
}