package com.example.gl_crash_course.repository.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "search_history_table")
data class SearchHistoryEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val searchDate: LocalDateTime
)

