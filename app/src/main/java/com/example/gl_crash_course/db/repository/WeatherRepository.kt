package com.example.gl_crash_course.db.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.gl_crash_course.db.AppDatabase
import com.example.gl_crash_course.db.model.WeatherEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WeatherRepository(application: Application) {
    private var database = AppDatabase.getAppDatabase(
        application
    )!!
    private var weatherDao = database.weatherDao()

    val allWeather: LiveData<List<WeatherEntry>> = weatherDao.getAll()

    fun insert(weatherEntry: WeatherEntry, callback: DbOperationCallback) = GlobalScope.launch(Dispatchers.Main) {
        async(Dispatchers.IO) {
            weatherDao?.insert(weatherEntry)
        }.invokeOnCompletion {
            callback.onFinishDb()
        }
    }

    fun update(weatherEntry: WeatherEntry, callback: DbOperationCallback) = GlobalScope.launch(Dispatchers.Main) {
        async(Dispatchers.IO) {
            weatherDao?.update(
                weatherEntry.temperature, weatherEntry.icon, weatherEntry.refreshed, weatherEntry.api_id,
                weatherEntry.description, weatherEntry.pressure, weatherEntry.humidity, weatherEntry.wind_speed,
                weatherEntry.sunrise, weatherEntry.sunset
            )
        }.invokeOnCompletion {
            callback.onFinishDb()
        }
    }

    fun delete(api_id: Int, callback: DbOperationCallback) = GlobalScope.launch(Dispatchers.Main) {
        async(Dispatchers.IO) {
            weatherDao?.delete(api_id)
        }.invokeOnCompletion {
            callback.onFinishDb()
        }
    }

    fun deleteWithoutCallback(api_id: Int) = GlobalScope.launch(Dispatchers.Main) {
        async(Dispatchers.IO) {
            weatherDao?.delete(api_id)
        }
    }

    fun get(api_id: Int): LiveData<WeatherEntry> {
        return weatherDao.get(api_id)
    }

    interface DbOperationCallback {
        fun onFinishDb()
    }

}