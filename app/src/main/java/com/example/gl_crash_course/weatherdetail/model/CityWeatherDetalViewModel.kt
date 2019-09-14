package com.example.gl_crash_course.weatherdetail.model


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import com.example.gl_crash_course.db.model.WeatherEntry
import com.example.gl_crash_course.db.repository.WeatherRepository

class CityWeatherDetalViewModel(application: Application) : AndroidViewModel(application) {

    private var weatherRepository: WeatherRepository = WeatherRepository(application)

    var liveDataWeather = MediatorLiveData<WeatherEntry>()

    fun loadWeather(id: Int) {
        var dataFromDb = weatherRepository.get(id)
        liveDataWeather.addSource(dataFromDb) { it ->
            it?.let {
                liveDataWeather.value = it
            }
        }
    }

}