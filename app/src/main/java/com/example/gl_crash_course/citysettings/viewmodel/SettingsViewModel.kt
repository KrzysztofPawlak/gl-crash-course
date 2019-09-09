package com.example.gl_crash_course.citysettings.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.gl_crash_course.api.ForecastService
import com.example.gl_crash_course.api.model.City
import com.example.gl_crash_course.repository.CityRepository
import com.example.gl_crash_course.repository.dao.CityEntry
import com.example.gl_crash_course.repository.dao.SearchHistoryEntry
import com.example.gl_crash_course.repository.dao.SearchHistoryRepository
import java.time.LocalDateTime

class SettingsViewModel(application: Application) : AndroidViewModel(application),
    ForecastService.GetWeatherByCityNameCallback {

    var searchedText = MutableLiveData<String>()
    var searchResult = MutableLiveData<City>()
    var mediatorCityLiveData = MediatorLiveData<List<CityEntry>>()
    var mediatorSearchHistoryLiveData = MediatorLiveData<List<SearchHistoryEntry>>()

    private var forecastService: ForecastService = ForecastService(application)
    private val cityRepository = CityRepository(application)
    private val searchHistoryRepository = SearchHistoryRepository(application)

    init {
        var dataCityListFromDb = cityRepository.allCities
        mediatorCityLiveData.addSource(dataCityListFromDb) { result: List<CityEntry>? ->
            result?.let {
                mediatorCityLiveData.value = it
            }
        }
        var dataSearchHistoryFromDb = searchHistoryRepository.lastHistoryEntries
        mediatorSearchHistoryLiveData.addSource(dataSearchHistoryFromDb) { result: List<SearchHistoryEntry>? ->
            result?.let {
                mediatorSearchHistoryLiveData.value = it
            }
        }
    }

    fun onShowData() {
        if (searchedText.value != null) {
            forecastService.getWeatherByCityName(searchedText.value.toString(), this)
            searchHistoryRepository.insert(SearchHistoryEntry(0, searchedText.value.toString(), LocalDateTime.now()))
        }
    }

    fun onAddData() {
        if (searchResult.value != null) {
            var city =
                CityEntry(0, searchResult.value!!.id, searchResult.value!!.name, searchResult.value!!.sys.country)

            if (!isAlreadyExists(city.api_id)) {
                cityRepository.insert(city)
            }
        }
    }

    fun deleteCityFromList(api_id: Int) {
        cityRepository.delete(api_id)
    }

    private fun isAlreadyExists(searchingId: Int): Boolean {
        return mediatorCityLiveData.value!!.any { it.api_id == searchingId }
    }

    override fun onWeatherLoaded(city: City?) {
        if (city != null) {
            searchResult.value = city
        }
    }
}