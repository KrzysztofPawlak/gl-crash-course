package com.example.gl_crash_course.forecastlist.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.gl_crash_course.model.Forecast
import com.example.gl_crash_course.service.repository.ForecastService

class ForecastViewModel(application: Application) : AndroidViewModel(application),
    ForecastService.GetForecastCallback {

    private var forecastService: ForecastService = ForecastService(application)

    private val limit = 3
    private var currentOffset = 0
    var refresh = MutableLiveData<Boolean>()

    private val mutableForecast by lazy {
        val liveData = MutableLiveData<Forecast>()
        getSubset()
        return@lazy liveData
    }

    fun getForecast(): MutableLiveData<Forecast> {
        return mutableForecast
    }

    fun incrementOffset() {
        currentOffset += 1
    }

    fun getSubset() {
        forecastService.getForecastForCities(this)
    }

    override fun onForecastLoaded(forecast: Forecast?) {
        val from = currentOffset * limit
        var to = from + limit

        if (to > forecast!!.list.size) {
            to = forecast!!.list.size
        }
        val trimForecast = Forecast(forecast.cod, limit, forecast!!.list.subList(0, to), forecast.message)
        mutableForecast.value = trimForecast
        refresh.value = false
    }

}