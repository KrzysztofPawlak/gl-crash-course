package com.example.gl_crash_course.citypicker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.gl_crash_course.api.WeatherService
import com.example.gl_crash_course.api.model.City
import com.example.gl_crash_course.db.repository.CityRepository
import com.example.gl_crash_course.db.model.CityEntry
import com.example.gl_crash_course.db.model.SearchHistoryEntry
import com.example.gl_crash_course.db.repository.SearchHistoryRepository
import com.example.gl_crash_course.db.repository.WeatherRepository
import java.time.LocalDateTime

class CityViewModel(application: Application) : AndroidViewModel(application),
    WeatherService.GetWeatherByCityNameCallback, WeatherService.FailureCallback {

    var searchedText = MutableLiveData<String>()
    var searchResult = MutableLiveData<City>()
    var mediatorCityLiveData = MediatorLiveData<List<CityEntry>>()
    var mediatorSearchHistoryLiveData = MediatorLiveData<List<SearchHistoryEntry>>()

    private var weatherService: WeatherService = WeatherService(application)
    private val cityRepository = CityRepository(application)
    private val weatherRepository = WeatherRepository(application)
    private val searchHistoryRepository = SearchHistoryRepository(application)

    var isSearchFinished: MutableLiveData<Boolean> = MutableLiveData()

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
        isSearchFinished.value = false
        if (searchedText.value != null) {
            weatherService.getWeatherByCityName(searchedText.value.toString(), this, this)
            searchHistoryRepository.insert(
                SearchHistoryEntry(
                    0,
                    searchedText.value.toString(),
                    LocalDateTime.now()
                )
            )
        } else {
            isSearchFinished.value = true
        }
    }

    fun onAddData() {
        if (searchResult.value != null) {
            var city =
                CityEntry(
                    0,
                    searchResult.value!!.id,
                    searchResult.value!!.name,
                    searchResult.value!!.sys.country
                )

            if (!isAlreadyExists(city.api_id)) {
                cityRepository.insert(city)
            }
        }
    }

    fun deleteCityFromList(api_id: Int) {
        cityRepository.delete(api_id)
        weatherRepository.deleteWithoutCallback(api_id)
    }

    private fun isAlreadyExists(searchingId: Int): Boolean {
        return mediatorCityLiveData.value!!.any { it.api_id == searchingId }
    }

    override fun onWeatherLoaded(city: City?) {
        if (city != null) {
            searchResult.value = city
        }
        isSearchFinished.value = true
    }

    override fun onResponseFailure() {
        isSearchFinished.value = true
    }
}