package com.example.gl_crash_course.weatherdetail.model


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.gl_crash_course.model.City
import com.example.gl_crash_course.service.repository.ForecastService

class CityWeatherDetalViewModel(application: Application) : AndroidViewModel(application),
    ForecastService.GetWeatherCallback {

    private var forecastService: ForecastService = ForecastService(application)

    var liveData = MutableLiveData<City>()

    fun getOne(id: String) {
        forecastService.getWeatherById(id, this)
    }

    override fun onWeatherLoaded(city: City?) {
        liveData.value = city
    }

}