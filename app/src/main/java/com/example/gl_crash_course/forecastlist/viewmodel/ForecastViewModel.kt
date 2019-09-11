package com.example.gl_crash_course.forecastlist.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.gl_crash_course.ForecastApiConst.UPDATE_TIME
import com.example.gl_crash_course.db.model.WeatherEntry
import com.example.gl_crash_course.api.model.Forecast
import com.example.gl_crash_course.db.repository.ForecastRepository
import com.example.gl_crash_course.api.ForecastService
import com.example.gl_crash_course.db.model.CityEntry
import com.example.gl_crash_course.db.repository.CityRepository
import java.time.*
import java.time.temporal.ChronoUnit
import java.util.concurrent.atomic.AtomicInteger

class ForecastViewModel(application: Application) : AndroidViewModel(application),
    ForecastService.GetForecastCallback, ForecastRepository.DbOperationCallback {

    private val forecastRepository = ForecastRepository(application)
    private val cityRepository = CityRepository(application)
    private var forecastService: ForecastService = ForecastService(application)

    private var counterCurrentOperationOnDb = AtomicInteger()

    var refresh = MutableLiveData<Boolean>()
    private val mutableForecast = MediatorLiveData<List<WeatherEntry>>()
    private val mutableCities = MediatorLiveData<List<CityEntry>>()

    init {
        var dataFromDb = forecastRepository.allWeather
        mutableForecast.addSource(dataFromDb) { result: List<WeatherEntry>? ->
            result?.let {
                mutableForecast.value = it
                fetchIfNeeded()
            }
        }
        var dataCitiesFromDb = cityRepository.allCities
        mutableForecast.addSource(dataCitiesFromDb) { result: List<CityEntry>? ->
            result?.let {
                mutableCities.value = it
                fetchIfNeeded()
            }
        }
    }

    fun getForecast(): MediatorLiveData<List<WeatherEntry>> {
        return mutableForecast
    }

    fun refreshList() {
        getData()
    }

    private fun fetchIfNeeded() {
        var allowedCurrentOperations = 0
        if (counterCurrentOperationOnDb.get() != allowedCurrentOperations) {
            return
        }

        if (mutableForecast.value.isNullOrEmpty() || mutableCities.value.isNullOrEmpty()) {
            return
        }

        var isWeatherOutdated = checkCurrentEntriesAreOutdated()
        var isWeatherListSizeIsLowerThanSizeFromCitiesList = mutableForecast.value!!.size < mutableCities.value!!.size
        var isWeatherListContainsCorrectCitiesEntries = checkWeatherCitiesIdsContainsAllCitiesIdsEntries()
        if (isWeatherListSizeIsLowerThanSizeFromCitiesList || isWeatherOutdated || !isWeatherListContainsCorrectCitiesEntries) {
            getData()
        }
    }

    private fun getData() {
        val ids = mutableCities.value!!.joinToString(separator = ",") { "${it.api_id}" }
        forecastService.getSetOfWeatherByIds(ids, this)
    }

    override fun onForecastLoaded(forecast: Forecast?) {

        val list = forecast!!.list.map {
            WeatherEntry(
                0,
                it.id,
                it.name,
                it.main.temp.toString(),
                it.weather[0].icon,
                LocalDateTime.now()
            )
        }

        list.stream()
            .filter { alreadyExists(it.api_id) }
            .forEach {
                counterCurrentOperationOnDb.getAndIncrement()
                forecastRepository.update(it, this)
            }

        list.stream()
            .filter { !alreadyExists(it.api_id) }
            .forEach {
                counterCurrentOperationOnDb.getAndIncrement()
                forecastRepository.insert(it, this)
            }

        mutableForecast.value!!.stream()
            .filter { !checkWeatherFromDbExistsInResponse(it.api_id, list) }
            .forEach {
                counterCurrentOperationOnDb.getAndIncrement()
                forecastRepository.delete(it.api_id, this)
            }

        refresh.value = false
    }

    private fun checkWeatherCitiesIdsContainsAllCitiesIdsEntries(): Boolean {
        var cityList = mutableCities.value as ArrayList<CityEntry>
        return mutableForecast.value!!.map { entry -> entry.api_id }.containsAll(cityList.map { entry -> entry.api_id })
    }

    private fun checkCurrentEntriesAreOutdated(): Boolean {
        return mutableForecast.value!!.any {
            ChronoUnit.MINUTES.between(it.refreshed.toInstant(OffsetDateTime.now().offset), Instant.now()) > UPDATE_TIME
        }
    }

    private fun checkWeatherFromDbExistsInResponse(dbId: Int, list: List<WeatherEntry>): Boolean {
        return list.any { it.api_id == dbId }
    }

    private fun alreadyExists(searchId: Int): Boolean {
        return mutableForecast.value!!.any { it.api_id == searchId }
    }

    override fun onFinishDb() {
        counterCurrentOperationOnDb.getAndDecrement()
    }

}