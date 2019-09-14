package com.example.gl_crash_course.db.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.gl_crash_course.db.AppDatabase
import com.example.gl_crash_course.db.model.SearchHistoryEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchHistoryRepository(application: Application) {
    private var database = AppDatabase.getAppDatabase(
        application
    )!!

    private var searchHistoryDao = database.searchHistoryDao()

    val lastHistoryEntries: LiveData<List<SearchHistoryEntry>> = searchHistoryDao.getLast3()

    fun insert(searchHistoryEntry: SearchHistoryEntry) = GlobalScope.launch(Dispatchers.Main) {
        async(Dispatchers.IO) {
            searchHistoryDao?.insert(searchHistoryEntry)
        }
    }
}