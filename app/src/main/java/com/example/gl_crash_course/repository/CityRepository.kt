package com.example.gl_crash_course.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.gl_crash_course.repository.dao.CityEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CityRepository(application: Application) {
    private var database = AppDatabase.getAppDatabase(
        application
    )!!

    private var cityDao = database.cityDao()

    val allCities: LiveData<List<CityEntry>> = cityDao.getAll()

    fun insert(cityEntry: CityEntry) = GlobalScope.launch(Dispatchers.Main) {
        async(Dispatchers.IO) {
            cityDao?.insert(cityEntry)
        }
    }

    fun delete(api_id: Int) = GlobalScope.launch(Dispatchers.Main) {
        async(Dispatchers.IO) {
            cityDao?.delete(api_id)
        }
    }
}