package com.example.gl_crash_course.repository.dao

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.gl_crash_course.repository.AppDatabase
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