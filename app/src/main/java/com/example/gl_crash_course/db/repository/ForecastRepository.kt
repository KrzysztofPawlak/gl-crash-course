package com.example.gl_crash_course.db.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.gl_crash_course.db.AppDatabase
import com.example.gl_crash_course.db.model.WeatherEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ForecastRepository(application: Application) {
    private var database = AppDatabase.getAppDatabase(
        application
    )!!
    private var weatherDao = database.weatherDao()

    val allWeather: LiveData<List<WeatherEntry>> = weatherDao.getAll()

    fun insert(weatherEntry: WeatherEntry, callback: DbOperationCallback) = GlobalScope.launch(Dispatchers.Main){
        async(Dispatchers.IO) {
            weatherDao?.insert(weatherEntry)
        }.invokeOnCompletion {
            callback.onFinishDb()
        }
    }

    fun update(weatherEntry: WeatherEntry, callback: DbOperationCallback) = GlobalScope.launch(Dispatchers.Main){
        async(Dispatchers.IO) {
            weatherDao?.update(weatherEntry.temperature, weatherEntry.icon, weatherEntry.refreshed, weatherEntry.api_id)
        }.invokeOnCompletion {
            callback.onFinishDb()
        }
    }

    fun delete(api_id: Int, callback: DbOperationCallback) = GlobalScope.launch(Dispatchers.Main){
        async(Dispatchers.IO) {
            weatherDao?.delete(api_id)
        }.invokeOnCompletion {
            callback.onFinishDb()
        }
    }

    interface DbOperationCallback {
        fun onFinishDb()
    }
}